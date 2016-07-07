package me.jpyfree.acfun.adapter.content;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import me.jpyfree.acfun.MyApplication;
import me.jpyfree.acfun.R;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.bean.ContentReply;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ContentReplyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context = MyApplication.getInstance().getApplicationContext();

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String userId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<ContentReply.DataEntity.PageEntity.Entity> replyList;

    public List<ContentReply.DataEntity.PageEntity.Entity> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ContentReply.DataEntity.PageEntity.Entity> replyList) {
        this.replyList = replyList;
        notifyDataSetChanged();
    }

    public void addReplyList(List<ContentReply.DataEntity.PageEntity.Entity> replyList) {
        this.replyList.addAll(replyList);
        notifyDataSetChanged();
    }

    public class ReplyInfoViewHolder extends BaseRecyclerViewHolder {

        private TextView userName;
        private TextView replyTime;
        private TextView replyText;
        private SimpleDraweeView userImg;
        private LinearLayout quoteLayout;
        private CardView replyCard;

        public ReplyInfoViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.content_reply_user_name);
            replyTime = (TextView) itemView.findViewById(R.id.content_reply_time);
            replyText = (TextView) itemView.findViewById(R.id.content_reply_text);
            userImg = (SimpleDraweeView) itemView.findViewById(R.id.content_reply_user_img);
            quoteLayout = (LinearLayout) itemView.findViewById(R.id.content_reply_quote_layout);
            replyCard = (CardView) itemView.findViewById(R.id.cv_content_reply);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (replyList != null) {
            return replyList.size();
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View replyItem = inflater.inflate(R.layout.cardview_content_reply, parent, false);

        return new ReplyInfoViewHolder(replyItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (replyList != null) {
            ContentReply.DataEntity.PageEntity.Entity entity = replyList.get(position);

            ((ReplyInfoViewHolder) holder).userName.setText("#" + entity.getFloor() + " " + entity.getUsername());
            ((ReplyInfoViewHolder) holder).replyTime.setText(MyUtils.getDateToString(entity.getTime()));
            ((ReplyInfoViewHolder) holder).replyText.setText(Html.fromHtml(entity.getContent()));
            ((ReplyInfoViewHolder) holder).userImg.setImageURI(entity.getAvatar());

            ((ReplyInfoViewHolder) holder).quoteLayout.removeAllViews();

            //引用层数,不超过3层
            int deep = entity.getDeep();
            int height = Math.min(deep, 3);

            List<View> quoteViewList = new ArrayList<>();

            while (height > 0) {
                entity = entity.getQuoteReply();

                if (entity == null) {
                    return;
                }
                //引用楼层
                TextView quoteFloor = new TextView(context);
                LinearLayout.LayoutParams quoteFloorLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                quoteFloorLP.setMargins(MyUtils.dp2px(context, 8f), MyUtils.dp2px(context, 8f), 0, 0);
                quoteFloor.setLayoutParams(quoteFloorLP);
                quoteFloor.setTextSize(12);
                quoteFloor.setTextColor(ContextCompat.getColor(context, R.color.font_blue));
                quoteFloor.setText("#" + entity.getFloor() + " " + entity.getUsername());
                //引用内容
                TextView quoteContent = new TextView(context);
                LinearLayout.LayoutParams quoteContentLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                quoteContentLP.setMargins(MyUtils.dp2px(context, 8f), MyUtils.dp2px(context, 4f), 0, MyUtils.dp2px(context, 4f));
                quoteContent.setLayoutParams(quoteContentLP);
                quoteContent.setTextColor(ContextCompat.getColor(context, R.color.font_light));
                quoteContent.setText(Html.fromHtml(entity.getContent()));
                //分割线
                View driverView = new View(context);
                LinearLayout.LayoutParams driverLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        MyUtils.dp2px(context, 1f));
                driverLP.setMargins(MyUtils.dp2px(context, 8f), 0, 0, 0);
                driverView.setLayoutParams(driverLP);
                driverView.setBackgroundResource(R.color.android_base);

                quoteViewList.add(driverView);
                quoteViewList.add(quoteContent);
                quoteViewList.add(quoteFloor);

                height--;
            }

            ListIterator<View> listIterator = quoteViewList.listIterator(quoteViewList.size());
            //逆向添加view
            while (listIterator.hasPrevious()) {
                ((ReplyInfoViewHolder) holder).quoteLayout.addView(listIterator.previous());
            }
        }

    }
}
