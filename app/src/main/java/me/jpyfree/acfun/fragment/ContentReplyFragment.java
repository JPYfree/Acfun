package me.jpyfree.acfun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.adapter.content.ContentReplyRecyclerViewAdapter;
import me.jpyfree.acfun.api.AcfunApi;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseFragment;
import me.jpyfree.acfun.bean.ContentReply;
import me.jpyfree.acfun.utils.MyUtils;
import me.jpyfree.acfun.utils.MyViewUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ContentReplyFragment extends BaseFragment {

    public static ContentReplyFragment newInstance(String contentId) {
        ContentReplyFragment fragment = new ContentReplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcfunString.CONTENT_ID, contentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager manager;

    private boolean isPrepared;
    private String contentId;
    private ContentReplyRecyclerViewAdapter adapter;
    private int pageNo = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_reply, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.content_reply_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.content_reply_swipe_refresh_layout);
        MyViewUtils.setSwipeRefreshLayoutColor(swipeRefreshLayout);

        contentId = getArguments().getString(AcfunString.CONTENT_ID);

        if (contentId == null) {
            MyUtils.showToastShort("网络异常,请重试");
            return null;
        }

        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new ContentReplyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        setListener();

        isPrepared = true;
//        lazyLoad();

        return view;
    }

    private void setListener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getHttpResult();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //标记是否向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //不滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    //判断是否滑动到底部
                    if (lastVisibleItem == totalItemCount - 1 && isSlidingToLast) {
                        //加载更多
                        getHttpResult();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx判断横向滑动,dy判断纵向滑动
                if (dy > 0) {
                    //大于0代表下滑
                    isSlidingToLast = true;
                }else {
                    isSlidingToLast = false;
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared || contentId == null) {
            return;
        }
        if (adapter.getReplyList() != null) {
            return;
        }

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                pageNo = 1;
                getHttpResult();
            }
        });
    }

    private void getHttpResult() {

        HashMap<String, String> httpParamsReply = AcfunApi.buildContentReplyUrl(contentId,
                AcfunString.PAGE_SIZE_NUM_50, String.valueOf(pageNo));

        Observable<ContentReply> observableReply = AcfunApi.getContentReply().onReplyResult(httpParamsReply);

        Subscription subscriptionReply = observableReply
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<ContentReply, Boolean>() {
                    @Override
                    public Boolean call(ContentReply contentReply) {
                        return MyUtils.isFragmentLive(getActivity(), ContentReplyFragment.this);
                    }
                })
                .filter(new Func1<ContentReply, Boolean>() {
                    @Override
                    public Boolean call(ContentReply contentReply) {
                        boolean hasReply = contentReply.getData().getPage().getList().size() > 0;
                        if (!hasReply) {
                            MyUtils.showToastShort("没评论啦");

                            swipeRefreshLayout.setRefreshing(false);
                            swipeRefreshLayout.setEnabled(true);
                        }
                        return hasReply;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<ContentReply, List<ContentReply.DataEntity.PageEntity.Entity>>() {
                    @Override
                    public List<ContentReply.DataEntity.PageEntity.Entity> call(ContentReply contentReply) {
                        return sortReplyList(contentReply);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ContentReply.DataEntity.PageEntity.Entity>>() {
                    @Override
                    public void call(List<ContentReply.DataEntity.PageEntity.Entity> entities) {
                        if (pageNo == 1) {
                            adapter.setReplyList(entities);
                        }else {
                            adapter.addReplyList(entities);
                        }

                        pageNo++;

                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        MyUtils.showToastShort("网络异常,请重试");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        compositeSubscription.add(subscriptionReply);

    }

    private List<ContentReply.DataEntity.PageEntity.Entity> sortReplyList(ContentReply contentReply) {
        Log.i("reply", "text");
        List<ContentReply.DataEntity.PageEntity.Entity> replyEntityList = new ArrayList<>();
        List<Integer> replyIdList = contentReply.getData().getPage().getList();
        HashMap<String, ContentReply.DataEntity.PageEntity.Entity> replyIdMap = contentReply.getData().getPage().getMap();

        //获取每个ID对应的回复内容
        for (Integer id : replyIdList) {
            replyEntityList.add(replyIdMap.get("c" + id));
        }

        //获取每个回复的引用
        for (ContentReply.DataEntity.PageEntity.Entity replyEntity : replyEntityList) {
            ContentReply.DataEntity.PageEntity.Entity currentReply = replyEntity;
            int quoteId = currentReply.getQuoteId();

            //获取引用的引用
            while (quoteId != 0 && currentReply.getQuoteReply() == null) {
                ContentReply.DataEntity.PageEntity.Entity quoteReply = replyIdMap.get("c" + quoteId);
                currentReply.setQuoteReply(quoteReply);
                currentReply = quoteReply;
                quoteId = currentReply.getQuoteId();
            }
        }
        return replyEntityList;
    }


}
