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
import me.jpyfree.acfun.adapter.RecommendRecyclerViewAdapter;
import me.jpyfree.acfun.api.AcfunApi;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseFragment;
import me.jpyfree.acfun.bean.RecommendBanner;
import me.jpyfree.acfun.bean.RecommendHot;
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
 * Created by Administrator on 2016/6/20.
 */
public class RecommendFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecommendRecyclerViewAdapter adapter;

    private boolean isPrepared;


    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_recommend, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recommend_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recommend_swipe_refresh_layout);
        MyViewUtils.setSwipeRefreshLayoutColor(swipeRefreshLayout);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        //设置itemView的跨度
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 | position == 1 | position == 6 | position == 9 | position == 12
                        | position == 15 | position == 18 | position == 21 | position == 24) {
                    return 2;
                }
                return 1;
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecommendRecyclerViewAdapter.MyDecoration());

        adapter = new RecommendRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

//        adapter.setSwipeRefreshLayout(swipeRefreshLayout);
        adapter.setOnItemClickListener(new RecommendRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String partitionType, String contentId) {
                if (partitionType != null) {
                    //启动分区页面
                    MyUtils.showToastShort(partitionType);
                }else if (contentId != null) {
                    //启动视频信息页面
//                    MyUtils.showToastShort(contentId);
                    ContentActivity.startActivity(getContext(), contentId);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(AcfunString.BANNER);
                getHttpResult(AcfunString.HOT);
                getHttpResult(AcfunString.ANIMATION);
                getHttpResult(AcfunString.FUN);
                getHttpResult(AcfunString.MUSIC);
                getHttpResult(AcfunString.GAME);
                getHttpResult(AcfunString.SCIENCE);
                getHttpResult(AcfunString.SPORT);
                getHttpResult(AcfunString.TV);
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
        if (adapter.getRecommendBanner() == null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(AcfunString.BANNER);
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshLayout.setEnabled(false);
                }
            });
        }
        if (adapter.getRecommendHot() == null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(AcfunString.HOT);
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshLayout.setEnabled(false);
                }
            });
        }
        if (adapter.getRecommendAnimation() == null) {
            getHttpResult(AcfunString.ANIMATION);
        }
        if (adapter.getRecommendFun() == null) {
            getHttpResult(AcfunString.FUN);
        }
        if (adapter.getRecommendMusic() == null) {
            getHttpResult(AcfunString.MUSIC);
        }
        if (adapter.getRecommendGame() == null) {
            getHttpResult(AcfunString.GAME);
        }
        if (adapter.getRecommendScience() == null) {
            getHttpResult(AcfunString.SCIENCE);
        }
        if (adapter.getRecommendSport() == null) {
            getHttpResult(AcfunString.SPORT);
        }
        if (adapter.getRecommendTv() == null) {
            getHttpResult(AcfunString.TV);
        }
    }


    private void getHttpResult(String httpGetType) {

        AcfunApi.onRecommend onRecommend = AcfunApi.getRecommend();

        switch (httpGetType) {
            //首页横幅
            case AcfunString.BANNER:
                HashMap<String, String> httpParamsBanner = AcfunApi.buildRecommendBannerUrl();

                Observable<RecommendBanner> observableBanner = onRecommend.onRecommendBannerResult(httpParamsBanner);

                Subscription subscriptionBanner = observableBanner.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendBanner, Boolean>() {
                            @Override
                            public Boolean call(RecommendBanner banner) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendBanner>() {
                            @Override
                            public void call(RecommendBanner recommendBanner) {
                                adapter.onRecommendBanner(recommendBanner);

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
                compositeSubscription.add(subscriptionBanner);
                break;

            //热门焦点
            case AcfunString.HOT:
                HashMap<String, String> httpParamsHot = AcfunApi.buildRecommendHotUrl(AcfunString.PAGE_NO_NUM_1);

                Observable<RecommendHot> observableHot = onRecommend.onRecommendHotResult(httpParamsHot);

                Subscription subscriptionHot = observableHot.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendHot, Boolean>() {
                            @Override
                            public Boolean call(RecommendHot recommendHot) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendHot>() {
                            @Override
                            public void call(RecommendHot recommendHot) {
                                adapter.onRecommendHot(recommendHot);

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
                break;

            //动画
            case AcfunString.ANIMATION:
                HashMap<String, String> httpParamsAnimation = AcfunApi.buildRecommendOtherUrl(AcfunString.ANIMATION,
                        AcfunString.LAST_POST, AcfunString.ONE_WEEK);

                Observable<RecommendOther> observableAnimation = onRecommend.onRecommendOtherResult(httpParamsAnimation);

                Subscription subscriptionAnimation = observableAnimation.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendOther, Boolean>() {
                            @Override
                            public Boolean call(RecommendOther recommendOther) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendOther>() {
                            @Override
                            public void call(RecommendOther recommendOther) {
                                adapter.onRecommendAnimation(recommendOther);

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
                compositeSubscription.add(subscriptionAnimation);
                break;

            //娱乐
            case AcfunString.FUN:
                HashMap<String, String> httpParamsFun = AcfunApi.buildRecommendOtherUrl(AcfunString.FUN,
                        AcfunString.LAST_POST, AcfunString.ONE_WEEK);

                Observable<RecommendOther> observableFun = onRecommend.onRecommendOtherResult(httpParamsFun);

                Subscription subscriptionFun = observableFun.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendOther, Boolean>() {
                            @Override
                            public Boolean call(RecommendOther recommendOther) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendOther>() {
                            @Override
                            public void call(RecommendOther recommendOther) {
                                adapter.onRecommendFun(recommendOther);

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
                compositeSubscription.add(subscriptionFun);
                break;

            //音乐
            case AcfunString.MUSIC:
                HashMap<String, String> httpParamsMusic = AcfunApi.buildRecommendOtherUrl(AcfunString.MUSIC,
                        AcfunString.LAST_POST, AcfunString.ONE_WEEK);

                Observable<RecommendOther> observableMusic = onRecommend.onRecommendOtherResult(httpParamsMusic);

                Subscription subscriptionMusic = observableMusic.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendOther, Boolean>() {
                            @Override
                            public Boolean call(RecommendOther recommendOther) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendOther>() {
                            @Override
                            public void call(RecommendOther recommendOther) {
                                adapter.onRecommendMusic(recommendOther);

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
                compositeSubscription.add(subscriptionMusic);
                break;

            //游戏
            case AcfunString.GAME:
                HashMap<String, String> httpParamsGame = AcfunApi.buildRecommendOtherUrl(AcfunString.GAME,
                        AcfunString.LAST_POST, AcfunString.ONE_WEEK);

                Observable<RecommendOther> observableGame = onRecommend.onRecommendOtherResult(httpParamsGame);

                Subscription subscriptionGame = observableGame.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendOther, Boolean>() {
                            @Override
                            public Boolean call(RecommendOther recommendOther) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendOther>() {
                            @Override
                            public void call(RecommendOther recommendOther) {
                                adapter.onRecommendGame(recommendOther);

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
                compositeSubscription.add(subscriptionGame);
                break;

            //科技
            case AcfunString.SCIENCE:
                HashMap<String, String> httpParamsScience = AcfunApi.buildRecommendOtherUrl(AcfunString.SCIENCE,
                        AcfunString.LAST_POST, AcfunString.ONE_WEEK);

                Observable<RecommendOther> observableScience = onRecommend.onRecommendOtherResult(httpParamsScience);

                Subscription subscriptionScience = observableScience.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendOther, Boolean>() {
                            @Override
                            public Boolean call(RecommendOther recommendOther) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendOther>() {
                            @Override
                            public void call(RecommendOther recommendOther) {
                                adapter.onRecommendScience(recommendOther);

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
                compositeSubscription.add(subscriptionScience);
                break;

            //体育
            case AcfunString.SPORT:
                HashMap<String, String> httpParamsSport = AcfunApi.buildRecommendOtherUrl(AcfunString.SPORT,
                        AcfunString.LAST_POST, AcfunString.ONE_WEEK);

                Observable<RecommendOther> observableSport = onRecommend.onRecommendOtherResult(httpParamsSport);

                Subscription subscriptionSport = observableSport.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendOther, Boolean>() {
                            @Override
                            public Boolean call(RecommendOther recommendOther) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendOther>() {
                            @Override
                            public void call(RecommendOther recommendOther) {
                                adapter.onRecommendSport(recommendOther);

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
                compositeSubscription.add(subscriptionSport);
                break;

            //影视
            case AcfunString.TV:
                HashMap<String, String> httpParamsTv = AcfunApi.buildRecommendOtherUrl(AcfunString.TV,
                        AcfunString.LAST_POST, AcfunString.ONE_WEEK);

                Observable<RecommendOther> observableTv = onRecommend.onRecommendOtherResult(httpParamsTv);

                Subscription subscriptionTv = observableTv.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RecommendOther, Boolean>() {
                            @Override
                            public Boolean call(RecommendOther recommendOther) {
                                return MyUtils.isFragmentLive(getActivity(), RecommendFragment.this);
                            }
                        })
                        .subscribe(new Action1<RecommendOther>() {
                            @Override
                            public void call(RecommendOther recommendOther) {
                                adapter.onRecommendTv(recommendOther);

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
                compositeSubscription.add(subscriptionTv);
                break;
        }
    }
}
