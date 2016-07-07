package me.jpyfree.acfun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.activity.ContentActivity;
import me.jpyfree.acfun.adapter.PartitionHotRvAdapter;
import me.jpyfree.acfun.api.AcfunApi;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseFragment;
import me.jpyfree.acfun.bean.RecommendHot;
import me.jpyfree.acfun.utils.MyUtils;
import me.jpyfree.acfun.utils.MyViewUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PartitionHotFragment extends BaseFragment {

    public static PartitionHotFragment newInstance() {
        return new PartitionHotFragment();
    }

    private RecyclerView recyclerView;
    private PartitionHotRvAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager manager;

    private boolean isPrepared;
    private int pageNo = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partition, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.partition_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.partition_swipe_refresh_layout);
        MyViewUtils.setSwipeRefreshLayoutColor(swipeRefreshLayout);

        manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new PartitionHotRvAdapter.MyDecoration());

        adapter = new PartitionHotRvAdapter();
        recyclerView.setAdapter(adapter);

        setListener();

        isPrepared = true;
        lazyLoad();

        return view;
    }

    private void setListener() {

        adapter.setOnItemClickListener(new PartitionHotRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String contentId) {
                ContentActivity.startActivity(getContext(), contentId);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(AcfunString.PAGE_NO_NUM_1);
                swipeRefreshLayout.setEnabled(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean isSlidingToLast;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    if (lastVisibleItem + 1 == totalItemCount && isSlidingToLast) {
                        getHttpResult(String.valueOf(pageNo));
                        swipeRefreshLayout.setRefreshing(true);
                        swipeRefreshLayout.setEnabled(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    isSlidingToLast = true;
                }else {
                    isSlidingToLast = false;
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        if (adapter.getRecommendHot() != null) {
            return;
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getHttpResult(AcfunString.PAGE_NO_NUM_1);
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.setEnabled(false);
            }
        });
    }

    private void getHttpResult(final String pageNum) {
        HashMap<String, String> httpParamsHot = AcfunApi.buildRecommendHotUrl(pageNum);

        Observable<RecommendHot> observableHot = AcfunApi.getRecommend().onRecommendHotResult(httpParamsHot);

        Subscription subscriptionHot = observableHot.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<RecommendHot, Boolean>() {
                    @Override
                    public Boolean call(RecommendHot recommendHot) {
                        return MyUtils.isFragmentLive(getActivity(), PartitionHotFragment.this);
                    }
                })
                .filter(new Func1<RecommendHot, Boolean>() {
                    @Override
                    public Boolean call(RecommendHot recommendHot) {
                        boolean isSuccess = recommendHot.getData() != null;
                        if (isSuccess) {
                            isSuccess = recommendHot.getData().getPage().getList().size() > 0;
                        }
                        return isSuccess;
                    }
                })
                .subscribe(new Action1<RecommendHot>() {
                    @Override
                    public void call(RecommendHot recommendHot) {
                        if (pageNum.equals(AcfunString.PAGE_NO_NUM_1)) {
                            adapter.setRecommendHot(recommendHot);
                        } else {
                            adapter.addData(recommendHot);
                        }
                        pageNo++;
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        MyUtils.showToastShort("刷新或网络异常");
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(true);
                    }
                });
        compositeSubscription.add(subscriptionHot);
    }
}
