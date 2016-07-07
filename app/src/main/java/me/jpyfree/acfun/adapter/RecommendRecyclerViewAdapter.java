package me.jpyfree.acfun.adapter;

import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.bean.RecommendBanner;
import me.jpyfree.acfun.bean.RecommendHot;
import me.jpyfree.acfun.bean.RecommendOther;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/20.
 */
public class RecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //itemView类型
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_NAVIGATION_TITLE = 1;
    private static final int TYPE_CARD_VIEW_HOT = 2;
    private static final int TYPE_CARD_VIEW_OTHER = 3;

    private RecommendBanner recommendBanner;
    private RecommendHot recommendHot;
    private RecommendOther recommendAnimation;
    private RecommendOther recommendFun;
    private RecommendOther recommendMusic;
    private RecommendOther recommendGame;
    private RecommendOther recommendScience;
    private RecommendOther recommendSport;
    private RecommendOther recommendTv;

//    private SwipeRefreshLayout swipeRefreshLayout;
//
//    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
//        this.swipeRefreshLayout = swipeRefreshLayout;
//    }

    public RecommendBanner getRecommendBanner() {
        return recommendBanner;
    }

    public RecommendHot getRecommendHot() {
        return recommendHot;
    }

    public RecommendOther getRecommendAnimation() {
        return recommendAnimation;
    }

    public RecommendOther getRecommendFun() {
        return recommendFun;
    }

    public RecommendOther getRecommendMusic() {
        return recommendMusic;
    }

    public RecommendOther getRecommendGame() {
        return recommendGame;
    }

    public RecommendOther getRecommendScience() {
        return recommendScience;
    }

    public RecommendOther getRecommendSport() {
        return recommendSport;
    }

    public RecommendOther getRecommendTv() {
        return recommendTv;
    }

    //传入item实例,刷新
    public void onRecommendBanner(RecommendBanner recommendBanner) {
        this.recommendBanner = recommendBanner;
        notifyDataSetChanged();
    }

    public void onRecommendHot(RecommendHot recommendHot) {
        this.recommendHot = recommendHot;
        notifyDataSetChanged();
    }

    public void onRecommendAnimation(RecommendOther recommendAnimation) {
        this.recommendAnimation = recommendAnimation;
        notifyDataSetChanged();
    }

    public void onRecommendFun(RecommendOther recommendFun) {
        this.recommendFun = recommendFun;
        notifyDataSetChanged();
    }

    public void onRecommendMusic(RecommendOther recommendMusic) {
        this.recommendMusic = recommendMusic;
        notifyDataSetChanged();
    }

    public void onRecommendGame(RecommendOther recommendGame) {
        this.recommendGame = recommendGame;
        notifyDataSetChanged();
    }

    public void onRecommendScience(RecommendOther recommendScience) {
        this.recommendScience = recommendScience;
        notifyDataSetChanged();
    }

    public void onRecommendSport(RecommendOther recommendSport) {
        this.recommendSport = recommendSport;
        notifyDataSetChanged();
    }

    public void onRecommendTv(RecommendOther recommendTv) {
        this.recommendTv = recommendTv;
        notifyDataSetChanged();
    }


    //定义itemview点击事件的interface,根据参数转向分区或视频信息页面
    public interface OnItemClickListener {
        void onItemClick(View view, String partitionType, String contentId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //首页Banner
    public class BannerViewHolder extends BaseRecyclerViewHolder {

        private ViewPager banner;
        private LinearLayout dotsLayout;
        private CardView cardViewBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (ViewPager) itemView.findViewById(R.id.viewpager_banner);
            dotsLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_dots);
            cardViewBanner = (CardView) itemView.findViewById(R.id.recommend_banner);
        }
    }

    //分区标题栏 Text&Button
    public class TitleViewHolder extends BaseRecyclerViewHolder {

        private TextView textTitle;
        private Button buttonMore;

        public TitleViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.fragment_rv_title_textview);
            buttonMore = (Button) itemView.findViewById(R.id.fragment_rv_title_button);
        }
    }

    //热门cardview
    public class HotViewHolder extends BaseRecyclerViewHolder {

        private TextView hotTitle;
        private TextView hotSubtitle;
        private SimpleDraweeView hotCover;
        private CardView cardViewHot;

        public HotViewHolder(View itemView) {
            super(itemView);
            hotTitle = (TextView) itemView.findViewById(R.id.cv_vertical_with_subtitle_tv_1);
            hotSubtitle = (TextView) itemView.findViewById(R.id.cv_vertical_with_subtitle_tv_2);
            hotCover = (SimpleDraweeView) itemView.findViewById(R.id.cv_vertical_with_subtitle_img);
            cardViewHot = (CardView) itemView.findViewById(R.id.cv_vertical_with_subtitle);
        }
    }

    //其他cardview
    public class OtherViewHolder extends BaseRecyclerViewHolder {

        private TextView otherTitle;
        private TextView otherSubtitleClick;
        private TextView otherSubtitleReply;
        private SimpleDraweeView otherCover;
        private CardView cardViewOther;

        public OtherViewHolder(View itemView) {
            super(itemView);
            otherTitle = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_title);
            otherSubtitleClick = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_click);
            otherSubtitleReply = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_reply);
            otherCover = (SimpleDraweeView) itemView.findViewById(R.id.cv_vertical_with_click_info_img);
            cardViewOther = (CardView) itemView.findViewById(R.id.cv_vertical_with_click_info);
        }
    }

    //获取GridView中itemView的类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }else if (isTitleType(position)) {
            return TYPE_NAVIGATION_TITLE;
        }else if (position == 2 | position == 3 | position == 4 | position == 5) {
            return TYPE_CARD_VIEW_HOT;
        }
        return TYPE_CARD_VIEW_OTHER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View banner = inflater.inflate(R.layout.recyclerview_recommend_banner, parent, false);
        View title = inflater.inflate(R.layout.recyclerview_title_with_button, parent, false);
        View hot = inflater.inflate(R.layout.cardview_vertical_with_subtitle, parent, false);
        View other = inflater.inflate(R.layout.cardview_vertical_with_click_info, parent, false);

        switch (viewType) {
            case TYPE_BANNER:
                return new BannerViewHolder(banner);
            case TYPE_NAVIGATION_TITLE:
                return new TitleViewHolder(title);
            case TYPE_CARD_VIEW_HOT:
                return new HotViewHolder(hot);
            case TYPE_CARD_VIEW_OTHER:
                return new OtherViewHolder(other);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {

            case TYPE_BANNER:
                if (recommendBanner != null && onItemClickListener != null) {
                    final ViewPager viewPager = ((BannerViewHolder) holder).banner;
                    RecommendBannerAdapter adapter = new RecommendBannerAdapter(recommendBanner,
                            viewPager, ((BannerViewHolder) holder).dotsLayout, onItemClickListener);
                    viewPager.setAdapter(adapter);
                    viewPager.setFocusable(true);
                    viewPager.requestFocus();

                    viewPager.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getActionMasked()) {
                                case MotionEvent.ACTION_MOVE:
                                    //允许底层接收父层的事件
                                    viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                                    break;
                            }
                            return false;
                        }
                    });
                }
                break;

            case TYPE_NAVIGATION_TITLE:
                ((TitleViewHolder) holder).textTitle.setText(AcfunString.getTitle(position));
                if (onItemClickListener != null) {
                    ((TitleViewHolder) holder).buttonMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v, AcfunString.getTitle(position), null);
                        }
                    });
                }
                break;

            case TYPE_CARD_VIEW_HOT:
                if (recommendHot != null && position == 2 | position == 3 | position == 4 | position == 5) {
                    final RecommendHot.DataEntity.PageEntity.ListEntity listEntity = recommendHot.getData()
                            .getPage().getList().get(position - 2);

                    ((HotViewHolder) holder).hotTitle.setText(listEntity.getTitle());
                    ((HotViewHolder) holder).hotSubtitle.setText(listEntity.getSubtitle());
                    ((HotViewHolder) holder).hotCover.setImageURI(listEntity.getCover());

                    if (onItemClickListener != null) {
                        ((HotViewHolder) holder).cardViewHot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.onItemClick(v, null, listEntity.getSpecialId());
                            }
                        });
                    }
                }
                break;

            case TYPE_CARD_VIEW_OTHER:
                //动画
                if (position == 7 | position == 8 && recommendAnimation != null) {
                    RecommendOther.DataEntity.PageEntity.ListEntity listEntity =
                            recommendAnimation.getData().getPage().getList().get(position - 7);

                    setCardViewOtherInfo(listEntity,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther, null,
                            listEntity.getContentId());
                }
                //娱乐
                else if (position == 10 | position == 11 && recommendFun != null) {
                    RecommendOther.DataEntity.PageEntity.ListEntity listEntity =
                            recommendFun.getData().getPage().getList().get(position - 10);
                    setCardViewOtherInfo(listEntity,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther, null,
                            listEntity.getContentId());
                }
                //音乐
                else if (position == 13 | position == 14 && recommendMusic != null) {
                    RecommendOther.DataEntity.PageEntity.ListEntity listEntity =
                            recommendMusic.getData().getPage().getList().get(position - 13);
                    setCardViewOtherInfo(listEntity,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther, null,
                            listEntity.getContentId());
                }
                //游戏
                else if (position == 16 | position == 17 && recommendGame != null) {
                    RecommendOther.DataEntity.PageEntity.ListEntity listEntity =
                            recommendGame.getData().getPage().getList().get(position - 16);
                    setCardViewOtherInfo(listEntity,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther, null,
                            listEntity.getContentId());
                }
                //科学
                else if (position == 19 | position == 20 && recommendScience != null) {
                    RecommendOther.DataEntity.PageEntity.ListEntity listEntity =
                            recommendScience.getData().getPage().getList().get(position - 19);
                    setCardViewOtherInfo(listEntity,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther, null,
                            listEntity.getContentId());
                }
                //体育
                else if (position == 22 | position == 23 && recommendSport != null) {
                    RecommendOther.DataEntity.PageEntity.ListEntity listEntity =
                            recommendSport.getData().getPage().getList().get(position - 22);
                    setCardViewOtherInfo(listEntity,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther, null,
                            listEntity.getContentId());
                }
                //影视
                else if (position == 25 | position == 26 && recommendTv != null) {
                    RecommendOther.DataEntity.PageEntity.ListEntity listEntity =
                            recommendTv.getData().getPage().getList().get(position - 25);
                    setCardViewOtherInfo(listEntity,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther, null,
                            listEntity.getContentId());
                }
                //空
                else {
                    setCardViewOtherInfo(null,
                            ((OtherViewHolder) holder).otherCover,
                            ((OtherViewHolder) holder).otherTitle,
                            ((OtherViewHolder) holder).otherSubtitleClick,
                            ((OtherViewHolder) holder).otherSubtitleReply);
                    setCardViewOtherOnClickListener(((OtherViewHolder) holder).cardViewOther,
                            null, null);
                }
        }
    }

    @Override
    public int getItemCount() {
        return 27;
    }

    //复用代码,设置cardview
    public void setCardViewOtherInfo(RecommendOther.DataEntity.PageEntity.ListEntity listEntity,
                                SimpleDraweeView otherCover,
                                TextView otherTitle,
                                TextView otherSubtitleClick,
                                TextView otherSubtitleReply) {
        if (listEntity != null) {
            otherCover.setImageURI(listEntity.getCover());
            otherTitle.setText(listEntity.getTitle());
            otherSubtitleClick.setText("点击: " + listEntity.getViews());
            otherSubtitleReply.setText("回复: " + listEntity.getComments());
        }else {
            otherCover.setImageURI("");
            otherTitle.setText("");
            otherSubtitleClick.setText("");
            otherSubtitleReply.setText("");
        }
    }

    //复用代码,设置点击事件
    public void setCardViewOtherOnClickListener(CardView cardViewOther, final String partitionType, final String contentId) {
        if (onItemClickListener != null) {
            if (partitionType == null && contentId == null) {
                cardViewOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtils.showToastShort("Nothing to show");
                    }
                });
            }
            cardViewOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, partitionType, contentId);
                }
            });
        }
    }


    //根据position判断是否选择的是分区标题
    public boolean isTitleType(int position) {
        return position == 1 | position == 6 | position == 9 | position == 12 | position == 15
                | position == 18 | position == 21 | position == 24;
    }

    //修饰cardview间的分割线
    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams())
                    .getViewLayoutPosition();
            int marginRight = MyUtils.dp2px(parent.getContext(), 7);
            if (position == 2 | position == 4 | position == 7 | position == 10 | position == 13
                    | position == 16 | position == 19 | position == 22 | position == 25) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
