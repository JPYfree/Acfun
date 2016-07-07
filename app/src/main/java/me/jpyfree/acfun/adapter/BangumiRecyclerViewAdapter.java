package me.jpyfree.acfun.adapter;

import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.bean.Bangumi;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class BangumiRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Bangumi bangumi;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String contentId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setBangumi(Bangumi bangumi) {
        this.bangumi = bangumi;
        notifyDataSetChanged();
    }

    public Bangumi getBangumi() {
        return bangumi;
    }

    public class BangumiViewHolder extends BaseRecyclerViewHolder {
        private SimpleDraweeView bangumiImg;
        private TextView bangumiTitle;
        private TextView bangumiNum;
        private CardView bangumiCard;

        public BangumiViewHolder(View itemView) {
            super(itemView);
            bangumiImg = (SimpleDraweeView) itemView.findViewById(R.id.cv_bangumi_img);
            bangumiTitle = (TextView) itemView.findViewById(R.id.cv_bangumi_title);
            bangumiNum = (TextView) itemView.findViewById(R.id.cv_bangumi_num);
            bangumiCard = (CardView) itemView.findViewById(R.id.cv_bangumi);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View bangumiView = inflater.inflate(R.layout.cardview_bangumi, parent, false);
        return new BangumiViewHolder(bangumiView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (bangumi != null) {
            final Bangumi.DataEntity.ListEntity listEntity = bangumi.getData().getList().get(position);
            if (holder instanceof BangumiViewHolder) {
                ((BangumiViewHolder) holder).bangumiImg.setImageURI(listEntity.getCover());
                ((BangumiViewHolder) holder).bangumiTitle.setText(listEntity.getTitle());
                ((BangumiViewHolder) holder).bangumiNum.setText("更新至 " + listEntity.getLastVideoName());
                ((BangumiViewHolder) holder).bangumiCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position, listEntity.getId());
                    }
                });
            }
        }
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int margin = MyUtils.dp2px(parent.getContext(), 8f);
            if (position % 2 == 0) {
                outRect.set(0, 0, margin, 0);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return bangumi != null ? bangumi.getData().getPageSize() : 0;
    }
}
