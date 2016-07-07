package me.jpyfree.acfun.adapter.content;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.bean.ContentInfo;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ContentInfoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIDEO_INFO = 0;
    private static final int VIDEO_ITEM = 1;
    private static final int VIDEO_DOWNLOAD = 2;

    private ContentInfo contentInfo;
    private List<ContentInfo.DataEntity.FullContentEntity.VideosEntity> videosList = new ArrayList<>();

    public void setContentInfo(ContentInfo contentInfo) {
        this.contentInfo = contentInfo;
        videosList = contentInfo.getData().getFullContent().getVideos();
        notifyDataSetChanged();
    }

    public ContentInfo getContentInfo() {
        return contentInfo;
    }

    private boolean isShowCheckBox;
    private boolean isSelectAll;
    private HashMap<Integer, Boolean> checkBoxCheckedArray = new HashMap<>();
    private List<Integer> checkedList = new ArrayList<>();
    private List<ContentInfo.DataEntity.FullContentEntity.VideosEntity> downloadList = new ArrayList<>();

    public void setShowCheckBox(boolean isShowCheckBox, boolean isSelectAll) {
        this.isShowCheckBox = isShowCheckBox;
        this.isSelectAll = isSelectAll;
        notifyDataSetChanged();
    }

    private OnVideoClickListener onVideoClickListener;
    private OnDownloadClickListener onDownloadClickListener;

    public interface OnVideoClickListener {
        void onClick(View view, int position, String userId, String videoId, String sourceId,
                     String sourceType, String sourceTitle);
    }

    public interface OnDownloadClickListener {
        void onClick(View view, int position,
                     List<ContentInfo.DataEntity.FullContentEntity.VideosEntity> downloadList);
    }

    public void setOnVideoClickListener(OnVideoClickListener onVideoClickListener) {
        this.onVideoClickListener = onVideoClickListener;
    }

    public void setOnDownloadClickListener(OnDownloadClickListener onDownloadClickListener) {
        this.onDownloadClickListener = onDownloadClickListener;
    }


    public class VideoInfoViewHolder extends BaseRecyclerViewHolder {

        private TextView title;
        private TextView description;
        private TextView click;
        private TextView stows;
        private SimpleDraweeView upImg;
        private TextView upName;
        private TextView upId;
        private LinearLayout upLayout;

        public VideoInfoViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.content_info_title);
            description = (TextView) itemView.findViewById(R.id.content_info_description);
            click = (TextView) itemView.findViewById(R.id.content_info_click);
            stows = (TextView) itemView.findViewById(R.id.content_info_stows);
            upImg = (SimpleDraweeView) itemView.findViewById(R.id.content_info_up_img);
            upName = (TextView) itemView.findViewById(R.id.content_info_up_name);
            upId = (TextView) itemView.findViewById(R.id.content_info_up_id);
            upLayout = (LinearLayout) itemView.findViewById(R.id.content_info_up_layout);

        }
    }

    public class VideoItemViewHolder extends BaseRecyclerViewHolder {

        private CardView itemCard;
        private TextView videoText;
        private CheckBox checkBox;

        public VideoItemViewHolder(View itemView) {
            super(itemView);
            itemCard = (CardView) itemView.findViewById(R.id.cv_content_video_item);
            videoText = (TextView) itemView.findViewById(R.id.content_video_item_text);
            checkBox = (CheckBox) itemView.findViewById(R.id.content_video_item_check);
        }
    }

    public class DownloadViewHolder extends BaseRecyclerViewHolder {

        private CardView downloadCard;

        public DownloadViewHolder(View itemView) {
            super(itemView);
            downloadCard = (CardView) itemView.findViewById(R.id.cv_content_download);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIDEO_INFO;
        }else if (isShowCheckBox && getItemCount() == position + 1) {
            return VIDEO_DOWNLOAD;
        }else {
            return VIDEO_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (isShowCheckBox) {
            return videosList.size() + 2;
        }else if (videosList.size() != 0) {
            return videosList.size() + 1;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View infoTitle = inflater.inflate(R.layout.cardview_content_info_title, parent, false);
        View videoItem = inflater.inflate(R.layout.cardview_content_video_item, parent, false);
        View download = inflater.inflate(R.layout.cardview_content_download, parent, false);

        switch (viewType) {
            case VIDEO_INFO:
                return new VideoInfoViewHolder(infoTitle);
            case VIDEO_ITEM:
                return new VideoItemViewHolder(videoItem);
            case VIDEO_DOWNLOAD:
                return new DownloadViewHolder(download);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case VIDEO_INFO:
                if (contentInfo != null) {
                    final ContentInfo.DataEntity.FullContentEntity contentEntity
                            = contentInfo.getData().getFullContent();

                    ((VideoInfoViewHolder) holder).title.setText(contentEntity.getTitle());
                    ((VideoInfoViewHolder) holder).description.setText(contentEntity.getDescription());
                    ((VideoInfoViewHolder) holder).click.setText("点击:" + contentEntity.getViews());
                    ((VideoInfoViewHolder) holder).stows.setText("收藏:" + contentEntity.getStows());
                    ((VideoInfoViewHolder) holder).upImg.setImageURI(contentEntity.getUser().getUserImg());
                    ((VideoInfoViewHolder) holder).upName.setText(contentEntity.getUser().getUsername());
                    ((VideoInfoViewHolder) holder).upId.setText("UID:" + contentEntity.getUser().getUserId());

                    if (onVideoClickListener != null) {
                        ((VideoInfoViewHolder) holder).upLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onVideoClickListener.onClick(v, position,
                                        String.valueOf(contentEntity.getUser().getUserId()),
                                        null, null, null, null);
                            }
                        });
                    }
                }
                break;

            case VIDEO_ITEM:
                if (videosList != null) {
                    final ContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity
                            = videosList.get(position - 1);

                    ((VideoItemViewHolder) holder).videoText.setText(position + "." + videosEntity.getName());

                    if (onVideoClickListener != null) {
                        ((VideoItemViewHolder) holder).itemCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onVideoClickListener.onClick(v, position, null,
                                        videosEntity.getVideoId(),
                                        videosEntity.getSourceId(),
                                        videosEntity.getSourceType(),
                                        videosEntity.getName());
                            }
                        });
                    }

                    //checkbox状态
                    if (!isShowCheckBox && !isSelectAll) {
                        ((VideoItemViewHolder) holder).checkBox.setVisibility(View.GONE);
                        ((VideoItemViewHolder) holder).checkBox.setChecked(false);
                    }else {
                        ((VideoItemViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
                        ((VideoItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CheckBox checkBox = (CheckBox) v;
                                checkBoxCheckedArray.put(position, checkBox.isChecked());
                            }
                        });
                        if (isSelectAll) {
                            ((VideoItemViewHolder) holder).checkBox.setChecked(true);
                            checkBoxCheckedArray.put(position, ((VideoItemViewHolder) holder).checkBox.isChecked());
                        }else {
                            ((VideoItemViewHolder) holder).checkBox.setChecked(false);
                        }
                    }
                }
                break;

            case VIDEO_DOWNLOAD:
                if (videosList != null && checkBoxCheckedArray != null) {

                    ((DownloadViewHolder) holder).downloadCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.showToastShort("TO DO");
                        }
                    });
                }
        }

    }
}
