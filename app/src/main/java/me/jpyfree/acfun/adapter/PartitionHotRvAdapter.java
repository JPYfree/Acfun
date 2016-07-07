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
import me.jpyfree.acfun.bean.RecommendHot;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PartitionHotRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecommendHot recommendHot;
    private List<RecommendHot.DataEntity.PageEntity.ListEntity> listEntityList = new ArrayList<>();

    public RecommendHot getRecommendHot() {
        return recommendHot;
    }

    public void setRecommendHot(RecommendHot recommendHot) {
        this.recommendHot = recommendHot;
        listEntityList.clear();
        listEntityList.addAll(recommendHot.getData().getPage().getList());
        notifyDataSetChanged();
    }

    public void addData(RecommendHot recommendHot) {
        listEntityList.addAll(recommendHot.getData().getPage().getList());
        notifyItemInserted(getItemCount());
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position, String contentId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class HotViewHolder extends BaseRecyclerViewHolder{

        private SimpleDraweeView viewImgHot;
        private TextView titleHot;
        private TextView subtitleHot;
        private CardView cardViewHot;

        public HotViewHolder(View itemView) {
            super(itemView);
            viewImgHot = (SimpleDraweeView) itemView.findViewById(R.id.cv_vertical_with_subtitle_img);
            titleHot = (TextView) itemView.findViewById(R.id.cv_vertical_with_subtitle_tv_1);
            subtitleHot = (TextView) itemView.findViewById(R.id.cv_vertical_with_subtitle_tv_2);
            cardViewHot = (CardView) itemView.findViewById(R.id.cv_vertical_with_subtitle);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_vertical_with_subtitle, parent, false);

        return new HotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HotViewHolder && listEntityList.size() > 0) {
            final RecommendHot.DataEntity.PageEntity.ListEntity listEntity = listEntityList.get(position);

            ((HotViewHolder) holder).viewImgHot.setImageURI(listEntity.getCover());
            ((HotViewHolder) holder).titleHot.setText(listEntity.getTitle());
            ((HotViewHolder) holder).subtitleHot.setText(listEntity.getSubtitle());

            if (onItemClickListener != null) {
                ((HotViewHolder) holder).cardViewHot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position, listEntity.getSpecialId());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return listEntityList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
