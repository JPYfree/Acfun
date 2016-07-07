package me.jpyfree.acfun.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import me.jpyfree.acfun.adapter.PartitionOtherRvAdapter;
import me.jpyfree.acfun.api.AcfunApi;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseFragment;
import me.jpyfree.acfun.bean.RecommendOther;
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
public class PartitionOtherFragment extends BaseFragment {

    public static PartitionOtherFragment newInstance(String partitionType) {
        PartitionOtherFragment fragment = new PartitionOtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcfunString.CHANNEL_IDS, partitionType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final int TYPE_HOT = 0;
    private static final int TYPE_ORDER = 1;

    private String partitionType;
    private int pageNo = 1;
    private boolean isPrepared;
    private PartitionOtherRvAdapter adapter;
    private GridLayoutManager manager;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partition, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.partition_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.partition_swipe_refresh_layout);
        MyViewUtils.setSwipeRefreshLayoutColor(swipeRefreshLayout);

        partitionType = getArguments().getString(AcfunString.CHANNEL_IDS);

        manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 1 || position == 2 || position == 3 || position == 4) {
                    return 1;
                }
                return 2;
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new PartitionOtherRvAdapter.MyDecoration());
        adapter = new PartitionOtherRvAdapter();
        recyclerView.setAdapter(adapter);

        setListener();

        isPrepared = true;
        lazyLoad();

        return view;
    }

    public void setHttpOrder() {
        getHttpResult(TYPE_ORDER, AcfunString.PAGE_NO_NUM_1);
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        if (adapter.getHotFocus() == null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(TYPE_HOT, null);
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshLayout.setEnabled(false);
                }
            });
        }
        if (adapter.getOtherOrder() == null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(TYPE_ORDER, AcfunString.PAGE_NO_NUM_1);
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshLayout.setEnabled(false);
                }
            });
        }
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(TYPE_HOT, null);
                getHttpResult(TYPE_ORDER, AcfunString.PAGE_NO_NUM_1);
                swipeRefreshLayout.setEnabled(false);
            }
        });

        adapter.setOnItemClickListener(new PartitionOtherRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String contentId) {
                ContentActivity.startActivity(getContext(), contentId);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean isSlidingToLast;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    if (lastVisibleItem == totalItemCount - 1 && isSlidingToLast) {
                        getHttpResult(TYPE_ORDER, String.valueOf(pageNo));
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

    private void getHttpResult(int cardViewType, final String pageNum) {

        SharedPreferences preferences = getActivity()
                .getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String order = preferences.getString(AcfunString.ORDER_BY, AcfunString.TIME_ORDER);

        AcfunApi.onPartition onPartition = AcfunApi.getPartition();

        if (cardViewType == TYPE_HOT) {
            HashMap<String, String> httpParamsHot = AcfunApi.buildPartitionUrl(partitionType,
                    AcfunString.PAGE_SIZE_NUM_10,
                    AcfunString.PAGE_NO_NUM_1,
                    AcfunString.POPULARITY,
                    AcfunString.ONE_WEEK);

            Observable<RecommendOther> observableHot = onPartition.onOtherResult(httpParamsHot);

            Subscription subscriptionHot = observableHot.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<RecommendOther, Boolean>() {
                        @Override
                        public Boolean call(RecommendOther recommendOther) {
                            return MyUtils.isFragmentLive(getActivity(), PartitionOtherFragment.this);
                        }
                    })
                    .filter(new Func1<RecommendOther, Boolean>() {
                        @Override
                        public Boolean call(RecommendOther recommendOther) {
                            boolean isSuccess = recommendOther.getData() != null;
                            if (isSuccess) {
                                isSuccess = recommendOther.getData().getPage().getList().size() > 0;
                            }
                            return isSuccess;
                        }
                    })
                    .subscribe(new Action1<RecommendOther>() {
                        @Override
                        public void call(RecommendOther recommendOther) {
                            adapter.setHotFocus(recommendOther);
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

        if (cardViewType == TYPE_ORDER) {
            HashMap<String, String> httpParamsOrder = AcfunApi.buildPartitionUrl(partitionType,
                    AcfunString.PAGE_SIZE_NUM_10,
                    pageNum,
                    order,
                    AcfunString.ONE_WEEK);

            Observable<RecommendOther> observableOrder = onPartition.onOtherResult(httpParamsOrder);

            Subscription subscriptionOrder = observableOrder.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<RecommendOther, Boolean>() {
                        @Override
                        public Boolean call(RecommendOther recommendOther) {
                            return MyUtils.isFragmentLive(getActivity(), PartitionOtherFragment.this);
                        }
                    })
                    .filter(new Func1<RecommendOther, Boolean>() {
                        @Override
                        public Boolean call(RecommendOther recommendOther) {
                            boolean isSuccess = recommendOther.getData() != null;
                            if (isSuccess) {
                                isSuccess = recommendOther.getData().getPage().getList().size() > 0;
                            }
                            return isSuccess;
                        }
                    })
                    .subscribe(new Action1<RecommendOther>() {
                        @Override
                        public void call(RecommendOther recommendOther) {
                            if (pageNum.equals(AcfunString.PAGE_NO_NUM_1)) {
                                adapter.setOtherOrder(recommendOther);
                            } else {
                                adapter.addData(recommendOther);
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
            compositeSubscription.add(subscriptionOrder);
        }
    }
}
