package me.jpyfree.acfun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.adapter.content.ContentInfoRecyclerViewAdapter;
import me.jpyfree.acfun.api.AcfunApi;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseFragment;
import me.jpyfree.acfun.bean.ContentInfo;
import me.jpyfree.acfun.config.BusConfig;
import me.jpyfree.acfun.utils.MyUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ContentInfoFragment extends BaseFragment {

    public static ContentInfoFragment newInstance(String contentId) {
        ContentInfoFragment fragment = new ContentInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcfunString.CONTENT_ID, contentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView recyclerView;
    private boolean isPrepared;
    private ContentInfoRecyclerViewAdapter adapter;
    private String contentId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_info, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.content_info_recycler_view);
        contentId = getArguments().getString(AcfunString.CONTENT_ID);

        BusConfig.getInstance().register(this);

        if (contentId == null) {
            MyUtils.showToastShort("网络异常,请重试");
            return null;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new ContentInfoRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        setClickListener();

        isPrepared = true;
        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared || contentId == null) {
            return;
        }
        if (adapter.getContentInfo() != null) {
            return;
        }
        getHttpResult();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusConfig.getInstance().unregister(this);
    }

    private void setClickListener() {

        adapter.setOnVideoClickListener(new ContentInfoRecyclerViewAdapter.OnVideoClickListener() {
            @Override
            public void onClick(View view, int position, String userId, String videoId, String sourceId, String sourceType, String sourceTitle) {
                if (position == 0) {
                    //转到用户界面
                    MyUtils.showToastShort("to" + userId);
                }else {
                    //播放视频
                    MyUtils.showToastShort("TO PLAY VIDEO");
                }
            }
        });

        adapter.setOnDownloadClickListener(new ContentInfoRecyclerViewAdapter.OnDownloadClickListener() {
            @Override
            public void onClick(View view, int position, List<ContentInfo.DataEntity.FullContentEntity.VideosEntity> downloadList) {
                //下载
                MyUtils.showToastShort("TO DOWNLOAD");
            }
        });
    }

    private void getHttpResult() {
//        Log.i("ContentInfoFragment", "TEXT");

        HashMap<String, String> httpParamsInfo = AcfunApi.buildContentInfoUrl(contentId);

        Observable<ContentInfo> observableInfo = AcfunApi.getContentInfo().onInfoResult(httpParamsInfo);

        Subscription subscriptionInfo = observableInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<ContentInfo, Boolean>() {
                    @Override
                    public Boolean call(ContentInfo contentInfo) {
                        return MyUtils.isFragmentLive(getActivity(), ContentInfoFragment.this);
                    }
                })
                .filter(new Func1<ContentInfo, Boolean>() {
                    @Override
                    public Boolean call(ContentInfo contentInfo) {
                        boolean isSuccess = contentInfo.isSuccess() && contentInfo.getStatus() == 200;
                        if (!isSuccess) {
                            MyUtils.showToastShort(contentInfo.getMsg());
                        }
                        return isSuccess;
                    }
                })
                .subscribe(new Action1<ContentInfo>() {
                    @Override
                    public void call(ContentInfo contentInfo) {
                        adapter.setContentInfo(contentInfo);
                        BusConfig.getInstance().post(contentInfo.getData().getFullContent());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        MyUtils.showToastShort("网络异常,请重试");
                    }
                });
        compositeSubscription.add(subscriptionInfo);
    }
}
