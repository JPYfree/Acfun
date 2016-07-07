package me.jpyfree.acfun.adapter;

import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.jpyfree.acfun.MyApplication;
import me.jpyfree.acfun.R;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseRecyclerViewHolder;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/27.
 */
public class NavigationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NAVIGATION_TITLE = 0;
    private static final int NAVIGATION_BUTTON = 1;

    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(View view, String partitionType);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class TitleViewHolder extends BaseRecyclerViewHolder {
        private TextView titleText;

        public TitleViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.navigation_title_text);
        }
    }

    public class ButtonViewHolder extends BaseRecyclerViewHolder {
        private ImageView buttonImg;
        private TextView buttonText;
        private CardView buttonCard;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            buttonImg = (ImageView) itemView.findViewById(R.id.navigation_button_img);
            buttonText = (TextView) itemView.findViewById(R.id.navigation_button_text);
            buttonCard = (CardView) itemView.findViewById(R.id.cv_navigation_button);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View titleView = inflater.inflate(R.layout.fragment_navigation_title, parent, false);
        View buttonView = inflater.inflate(R.layout.cardview_navigation_button, parent, false);

        if (viewType == NAVIGATION_TITLE) {
            return new TitleViewHolder(titleView);
        }else if (viewType == NAVIGATION_BUTTON) {
            return new ButtonViewHolder(buttonView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case NAVIGATION_TITLE:
                switch (position) {
                    case 0:
                        ((TitleViewHolder) holder).titleText.setText(AcfunString.TITLE_HOT_RANKING);
                        break;
                    case 3:
                        ((TitleViewHolder) holder).titleText.setText(AcfunString.TITLE_PARTITION);
                        break;
                }
                break;
            case NAVIGATION_BUTTON:
                switch (position) {
                    case 1:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.hot,
                                R.color.md_yellow_700,
                                AcfunString.TITLE_HOT);
                        break;
                    case 2:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.ranking,
                                R.color.md_blue_700,
                                AcfunString.TITLE_RANKING);
                        break;
                    case 4:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.animation,
                                R.color.md_blue_grey_700,
                                AcfunString.TITLE_ANIMATION);
                        break;
                    case 5:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.fun,
                                R.color.md_cyan_700,
                                AcfunString.TITLE_FUN);
                        break;
                    case 6:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.music,
                                R.color.md_deep_orange_700,
                                AcfunString.TITLE_MUSIC);
                        break;
                    case 7:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.game,
                                R.color.md_deep_purple_700,
                                AcfunString.TITLE_GAME);
                        break;
                    case 8:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.science,
                                R.color.md_green_700,
                                AcfunString.TITLE_SCIENCE);
                        break;
                    case 9:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.sport,
                                R.color.md_grey_700,
                                AcfunString.TITLE_SPORT);
                        break;
                    case 10:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.tv,
                                R.color.md_purple_700,
                                AcfunString.TITLE_TV);
                        break;
                    case 11:
                        setButtonInfo(((ButtonViewHolder) holder).buttonImg,
                                ((ButtonViewHolder) holder).buttonText,
                                ((ButtonViewHolder) holder).buttonCard,
                                R.drawable.essay,
                                R.color.md_lime_700,
                                AcfunString.TITLE_ESSAY);
                        break;
                }
        }
    }

    private void setButtonInfo(ImageView buttonImg, TextView buttonText, CardView buttonCard,
                               int drawable, int color, final String partitionType) {
        buttonImg.setBackgroundResource(drawable);
        buttonText.setText(partitionType);
        buttonCard.setCardBackgroundColor(ContextCompat.getColor(MyApplication.getInstance(), color));
        buttonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v, partitionType);
            }
        });
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = MyUtils.dp2px(parent.getContext(), 10f);
            if (position == 1 || position == 4 || position == 6 || position == 8 || position == 10) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 | position == 3) {
            return NAVIGATION_TITLE;
        }else {
            return NAVIGATION_BUTTON;
        }
    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
