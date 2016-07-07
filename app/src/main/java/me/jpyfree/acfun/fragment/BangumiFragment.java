package me.jpyfree.acfun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.adapter.BangumiRecyclerViewAdapter;
import me.jpyfree.acfun.api.AcfunApi;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseFragment;
import me.jpyfree.acfun.bean.Bangumi;
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
public class BangumiFragment extends BaseFragment{


    public static BangumiFragment newInstance() {
        return new BangumiFragment();
    }

    private boolean isPrepared;
    private BangumiRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_bangumi, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.bangumi_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.bangumi_swipe_refresh_layout);
        MyViewUtils.setSwipeRefreshLayoutColor(swipeRefreshLayout);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new BangumiRecyclerViewAdapter.MyDecoration());

        adapter = new BangumiRecyclerViewAdapter();
        adapter.setOnItemClickListener(new BangumiRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String contentId) {
                //转到新番页面
                MyUtils.showToastShort("TO DO");
            }
        });
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult();
                swipeRefreshLayout.setEnabled(false);
            }
        });

        isPrepared = true;
        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {

        if (!isVisible || !isPrepared) {
            return;
        }
        if (adapter.getBangumi() != null) {
            return;
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getHttpResult();
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.setEnabled(false);
            }
        });
    }

    private void getHttpResult() {
        HashMap<String, String> httpParamsBangumi = AcfunApi.buildBangumiUrl(AcfunString.BANGUMI_TYPES_ANIMATION);

        Observable<Bangumi> observableBangumi = AcfunApi.getBangumi().onBangumiResult(httpParamsBangumi);

        Subscription subscriptionBangumi = observableBangumi.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Bangumi, Boolean>() {
                    @Override
                    public Boolean call(Bangumi bangumi) {
                        return MyUtils.isFragmentLive(getActivity(), BangumiFragment.this);
                    }
                })
                .subscribe(new Action1<Bangumi>() {
                    @Override
                    public void call(Bangumi bangumi) {
                        adapter.setBangumi(bangumi);

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
        compositeSubscription.add(subscriptionBangumi);
    }
}
