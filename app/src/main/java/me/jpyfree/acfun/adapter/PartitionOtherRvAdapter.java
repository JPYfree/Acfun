package me.jpyfree.acfun.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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

import me.jpyfree.acfun.MyApplication;
import me.jpyfree.acfun.R;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.bean.RecommendOther;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PartitionOtherRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_HOT = 1;
    private static final int TYPE_ORDER = 2;

    private RecommendOther hotFocus;
    private RecommendOther otherOrder;

    private List<RecommendOther.DataEntity.PageEntity.ListEntity> listEntityList = new ArrayList<>();

    public RecommendOther getHotFocus() {
        return hotFocus;
    }

    public void setHotFocus(RecommendOther hotFocus) {
        this.hotFocus = hotFocus;
        notifyDataSetChanged();
    }

    public RecommendOther getOtherOrder() {
        return otherOrder;
    }

    public void setOtherOrder(RecommendOther otherOrder) {
        this.otherOrder = otherOrder;

        listEntityList.clear();
        listEntityList.addAll(this.otherOrder.getData().getPage().getList());
        notifyDataSetChanged();
    }

    public void addData(RecommendOther otherOrder) {
        listEntityList.addAll(otherOrder.getData().getPage().getList());
        notifyItemInserted(getItemCount());
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String contentId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class TitleViewHolder extends BaseRecyclerViewHolder {
        private TextView titleText;

        public TitleViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.partition_title_text);
        }
    }

    public class HotFocusViewHolder extends BaseRecyclerViewHolder {
        private TextView title;
        private TextView clickNo;
        private TextView replyNo;
        private SimpleDraweeView viewImg;
        private CardView cardHot;

        public HotFocusViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_title);
            clickNo = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_click);
            replyNo = (TextView) itemView.findViewById(R.id.cv_vertical_with_click_info_tv_reply);
            viewImg = (SimpleDraweeView) itemView.findViewById(R.id.cv_vertical_with_click_info_img);
            cardHot = (CardView) itemView.findViewById(R.id.cv_vertical_with_click_info);
        }
    }

    public class OtherOrderViewHolder extends BaseRecyclerViewHolder {
        private TextView titleOther;
        private TextView upOther;
        private TextView clickOther;
        private TextView replyOther;
        private SimpleDraweeView viewImgOther;
        private CardView cardOther;

        public OtherOrderViewHolder(View itemView) {
            super(itemView);
            titleOther = (TextView) itemView.findViewById(R.id.cv_horizontal_tv_title);
            upOther = (TextView) itemView.findViewById(R.id.cv_horizontal_tv_up);
            clickOther = (TextView) itemView.findViewById(R.id.cv_horizontal_tv_click);
            replyOther = (TextView) itemView.findViewById(R.id.cv_horizontal_tv_reply);
            viewImgOther = (SimpleDraweeView) itemView.findViewById(R.id.cv_horizontal_img);
            cardOther = (CardView) itemView.findViewById(R.id.cv_horizontal_with_click_info);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View title = inflater.inflate(R.layout.fragment_partition_title, parent, false);
        View hot = inflater.inflate(R.layout.cardview_vertical_with_click_info, parent, false);
        View other = inflater.inflate(R.layout.cardview_horizontal_with_click_info, parent, false);

        switch (viewType) {
            case TYPE_TITLE:
                return new TitleViewHolder(title);
            case TYPE_HOT:
                return new HotFocusViewHolder(hot);
            case TYPE_ORDER:
                return new OtherOrderViewHolder(other);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {

            case TYPE_TITLE:
                if (position == 0) {
                    ((TitleViewHolder) holder).titleText.setText(AcfunString.TITLE_HOT);
                }else {
                    Context context = MyApplication.getInstance().getApplicationContext();
                    SharedPreferences preferences = context.getSharedPreferences(
                            context.getString(R.string.app_name), Context.MODE_PRIVATE);
                    String title = preferences.getString(AcfunString.TITLE, AcfunString.TITLE_TIME_ORDER);
                    ((TitleViewHolder) holder).titleText.setText(title);
                }
                break;

            case TYPE_HOT:
                if (hotFocus != null) {
                    final RecommendOther.DataEntity.PageEntity.ListEntity listEntity
                            = hotFocus.getData().getPage().getList().get(position - 1);

                    if (listEntity == null) {
                        return;
                    }

                    ((HotFocusViewHolder) holder).viewImg.setImageURI(listEntity.getCover());
                    ((HotFocusViewHolder) holder).title.setText(listEntity.getTitle());
                    ((HotFocusViewHolder) holder).clickNo.setText("点击 " + listEntity.getViews());
                    ((HotFocusViewHolder) holder).replyNo.setText("回复 " + listEntity.getComments());

                    if (onItemClickListener != null) {
                        ((HotFocusViewHolder) holder).cardHot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.onItemClick(v, position, listEntity.getContentId());
                            }
                        });
                    }
                }
                break;

            case TYPE_ORDER:
                if (listEntityList.size() != 0) {
                    final RecommendOther.DataEntity.PageEntity.ListEntity listEntity
                            = listEntityList.get(position - 6);

                    ((OtherOrderViewHolder) holder).viewImgOther
                            .setImageURI(listEntity.getCover());
                    ((OtherOrderViewHolder) holder).titleOther
                            .setText(listEntity.getTitle());
                    ((OtherOrderViewHolder) holder).upOther
                            .setText("UP主 " + listEntity.getUser().getUsername());
                    ((OtherOrderViewHolder) holder).clickOther
                            .setText("点击 " + listEntity.getViews());
                    ((OtherOrderViewHolder) holder).replyOther
                            .setText("回复 " + listEntity.getComments());

                    if (onItemClickListener != null) {
                        ((OtherOrderViewHolder) holder).cardOther.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.onItemClick(v, position, listEntity.getContentId());
                            }
                        });
                    }
                }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isTitleType(position)) {
            return TYPE_TITLE;
        }else if (isHotType(position)) {
            return TYPE_HOT;
        }else {
            return TYPE_ORDER;
        }
    }

    @Override
    public int getItemCount() {
        return listEntityList.size() + 6;
    }

    public boolean isTitleType(int position) {
        return position == 0 || position == 5;
    }

    public boolean isHotType(int position) {
        return position == 1 || position == 2 || position == 3 || position == 4;
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = MyUtils.dp2px(parent.getContext(), 7f);
            if (position == 1 || position == 3) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
