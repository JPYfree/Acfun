package me.jpyfree.acfun.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.bean.Essay;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class EssayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Essay essay;
    private List<Essay.DataEntity.PageEntity.ListEntity> listEntityList = new ArrayList<>();

    public Essay getEssay() {
        return essay;
    }

    public void setEssay(Essay essay) {
        this.essay = essay;
        listEntityList.clear();
        listEntityList.addAll(essay.getData().getPage().getList());
        notifyDataSetChanged();
    }

    public void addData(Essay essay) {
        listEntityList.addAll(essay.getData().getPage().getList());
        notifyItemInserted(getItemCount());
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String contentId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class EssayViewHolder extends BaseRecyclerViewHolder {
        private TextView title;
        private TextView name;
        private TextView time;
        private TextView click;
        private TextView reply;
        private CardView essayCard;

        public EssayViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cv_essay_title);
            name = (TextView) itemView.findViewById(R.id.cv_essay_name);
            time = (TextView) itemView.findViewById(R.id.cv_essay_time);
            click = (TextView) itemView.findViewById(R.id.cv_essay_click);
            reply = (TextView) itemView.findViewById(R.id.cv_essay_reply);
            essayCard = (CardView) itemView.findViewById(R.id.cv_essay);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View essayView = inflater.inflate(R.layout.cardview_essay, parent, false);
        return new EssayViewHolder(essayView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (listEntityList != null) {
            final Essay.DataEntity.PageEntity.ListEntity listEntity = listEntityList.get(position);
            if (holder instanceof EssayViewHolder) {
                ((EssayViewHolder) holder).title.setText(listEntity.getTitle());
                ((EssayViewHolder) holder).name.setText(listEntity.getUser().getUsername());
                ((EssayViewHolder) holder).time.setText(MyUtils.getDateToStringMDHM(listEntity.getReleaseDate()));
                ((EssayViewHolder) holder).click.setText("点击 " + listEntity.getViews());
                ((EssayViewHolder) holder).reply.setText("回复 " + listEntity.getComments());

                if (onItemClickListener != null) {
                    ((EssayViewHolder) holder).essayCard.setOnClickListener(new View.OnClickListener() {
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
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return listEntityList.size();
    }
}
