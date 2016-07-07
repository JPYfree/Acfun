package me.jpyfree.acfun.adapter;

import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.bean.RecommendOther;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PartitionRankRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecommendOther recommendOther;
    private List<RecommendOther.DataEntity.PageEntity.ListEntity> listEntityList = new ArrayList<>();

    public RecommendOther getRecommendOther() {
        return recommendOther;
    }

    public void setRecommendOther(RecommendOther recommendOther) {
        this.recommendOther = recommendOther;
        listEntityList.clear();
        listEntityList.addAll(recommendOther.getData().getPage().getList());
        notifyDataSetChanged();
    }

    public void addData(RecommendOther recommendOther) {
        listEntityList.addAll(recommendOther.getData().getPage().getList());
        notifyItemInserted(getItemCount());
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String contentId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class RankViewHolder extends BaseRecyclerViewHolder {

        private SimpleDraweeView viewImgRank;
        private TextView titleRank;
        private TextView clickRank;
        private TextView replyRank;
        private CardView cardViewRank;

        public RankViewHolder(View itemView) {
            super(itemView);
            viewImgRank = (SimpleDraweeView) itemView.findViewById(R.id.cv_vertical_with_click_info_img);
            titleRank = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_title);
            clickRank = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_click);
            replyRank = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_reply);
            cardViewRank = (CardView) itemView.findViewById(R.id.cv_vertical_with_click_info);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_vertical_with_click_info, parent, false);

        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RankViewHolder && listEntityList.size() > 0) {
            final RecommendOther.DataEntity.PageEntity.ListEntity listEntity = listEntityList.get(position);
            ((RankViewHolder) holder).viewImgRank.setImageURI(listEntity.getCover());
            ((RankViewHolder) holder).titleRank.setText(listEntity.getTitle());
            ((RankViewHolder) holder).clickRank.setText("点击 " + listEntity.getViews());
            ((RankViewHolder) holder).replyRank.setText("回复 " + listEntity.getComments());

            if (onItemClickListener != null) {
                ((RankViewHolder) holder).cardViewRank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position, listEntity.getContentId());
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return listEntityList.size();
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = MyUtils.dp2px(parent.getContext(), 8);
            if (position % 2 == 0) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
