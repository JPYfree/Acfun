package me.jpyfree.acfun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.adapter.EssayRecyclerViewAdapter;
import me.jpyfree.acfun.api.AcfunApi;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseFragment;
import me.jpyfree.acfun.bean.Essay;
import me.jpyfree.acfun.utils.MyUtils;
import me.jpyfree.acfun.utils.MyViewUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/20.
 */
public class EssayFragment extends BaseFragment{

    public static EssayFragment newInstance(String channelType) {
        EssayFragment fragment = new EssayFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcfunString.CHANNEL_IDS, channelType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean isPrepared;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EssayRecyclerViewAdapter adapter;
    private LinearLayoutManager manager;
    private String partitionType;
    private int pageNo = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_essay, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.essay_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.essay_swipe_refresh_layout);
        MyViewUtils.setSwipeRefreshLayoutColor(swipeRefreshLayout);

        partitionType = getArguments().getString(AcfunString.CHANNEL_IDS);

        manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        adapter = new EssayRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        setListener();

        isPrepared = true;
        lazyLoad();

        return view;
    }

    private void setListener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getHttpResult();
                swipeRefreshLayout.setEnabled(false);
            }
        });

        adapter.setOnItemClickListener(new EssayRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String contentId) {
                //转到文章页面
                MyUtils.showToastShort("TO DO");
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    if (lastVisibleItem == totalItemCount - 1 && isSlidingToLast) {
                        //加载更多
                        getHttpResult();
                        swipeRefreshLayout.setRefreshing(true);
                        swipeRefreshLayout.setEnabled(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
        if (adapter.getEssay() != null) {
            return;
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getHttpResult();
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }


    private void getHttpResult() {
        HashMap<String, String> httpParamsEssay = AcfunApi.buildPartitionUrl(partitionType,
                AcfunString.PAGE_SIZE_NUM_20, String.valueOf(pageNo),
                AcfunString.POPULARITY, AcfunString.ONE_WEEK);

        Observable<Essay> observableEssay = AcfunApi.getPartition().onEssayResult(httpParamsEssay);

        Subscription subscriptionEssay = observableEssay.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Essay, Boolean>() {
                    @Override
                    public Boolean call(Essay essay) {
                        return MyUtils.isFragmentLive(getActivity(), EssayFragment.this);
                    }
                })
                .subscribe(new Action1<Essay>() {
                    @Override
                    public void call(Essay essay) {
                        if (pageNo != 1) {
                            adapter.addData(essay);
                        } else {
                            adapter.setEssay(essay);
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
        compositeSubscription.add(subscriptionEssay);
    }
}
