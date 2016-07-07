package me.jpyfree.acfun.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import me.jpyfree.acfun.MyApplication;
import me.jpyfree.acfun.R;
import me.jpyfree.acfun.bean.RecommendBanner;
import me.jpyfree.acfun.utils.MyUtils;

/**
 * Created by Administrator on 2016/6/20.
 */
public class RecommendBannerAdapter extends PagerAdapter {

    private Context context = MyApplication.getInstance().getApplicationContext();
    private Resources resources = context.getResources();
    private List<View> viewItems = new ArrayList<>();
    private List<View> dotsList = new ArrayList<>();
    private int vpTotalNum;
    private int vpNum;

    public RecommendBannerAdapter(RecommendBanner banner, ViewPager viewPager, LinearLayout dotsLayout,
                                  final RecommendRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        super();
        final List<RecommendBanner.DataEntity.ListEntity> bannerInfo = banner.getData().getList();
        vpTotalNum = bannerInfo.size();

        //圆点图
        int dotsPx = MyUtils.dp2px(context, 7f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotsPx, dotsPx);
        layoutParams.setMargins(dotsPx / 2, dotsPx, dotsPx, dotsPx);

        if (vpTotalNum != 0) {
            //添加滚动监听,改变dots状态
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    vpNum = position % vpTotalNum;
                    for (View view : dotsList) {
                        if (vpNum == (int) view.getTag()) {
                            view.setSelected(true);
                        }else {
                            view.setSelected(false);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            for (int i = 0; i < vpTotalNum; i++) {
                String coverUrl = bannerInfo.get(i).getCover();
                Uri uri = Uri.parse(coverUrl);

                //创建圆点图
                View dotsView = new View(context);
                dotsView.setLayoutParams(layoutParams);
                dotsView.setTag(i);
                dotsView.setBackgroundResource(R.drawable.banner_dots_selector);

                //默认选择第一个
                if (i == 0) {
                    dotsView.setSelected(true);
                }
                dotsList.add(dotsView);

                //将圆点图添加到dotsLinearLayout中,并需防止刷新导致添加过多圆点
                if (dotsLayout.getChildCount() < vpTotalNum) {
                    dotsLayout.addView(dotsView, i);
                }

                //viewPager的itemView
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
                GenericDraweeHierarchy bannerGDH = new GenericDraweeHierarchyBuilder(resources)
                        .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                        .build();
                simpleDraweeView.setHierarchy(bannerGDH);
                simpleDraweeView.setImageURI(uri);
                simpleDraweeView.setSelected(true);
                simpleDraweeView.setFocusable(true);
                viewItems.add(simpleDraweeView);

                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转至详细页面
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, null, bannerInfo.get(vpNum).getSpecialId());
                        }
                    }
                });
            }
        }
    }

    //将viewpager的页数设为无限
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (viewItems.size() != 0 && vpTotalNum != 0) {
            View view = viewItems.get(position % vpTotalNum);
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewItems.get(position % vpTotalNum));
    }
}
