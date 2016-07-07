package me.jpyfree.acfun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.otto.Subscribe;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.adapter.content.ContentFragmentAdapter;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseActivity;
import me.jpyfree.acfun.bean.ContentInfo;
import me.jpyfree.acfun.config.BusConfig;
import me.jpyfree.acfun.utils.MyUtils;
import me.jpyfree.acfun.utils.MyViewUtils;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ContentActivity extends BaseActivity {

    public static void startActivity(Context context, String contentId) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(AcfunString.CONTENT_ID, contentId);
        context.startActivity(intent);
    }

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SimpleDraweeView titleImg;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String contentId;
    private ContentFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        toolbar = (Toolbar) findViewById(R.id.content_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        titleImg = (SimpleDraweeView) findViewById(R.id.content_title_img);
        tabLayout = (TabLayout) findViewById(R.id.content_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.content_viewpager);

        BusConfig.getInstance().register(this);

        contentId = getIntent().getStringExtra(AcfunString.CONTENT_ID);

        MyViewUtils.setToolbar(ContentActivity.this, toolbar, true);
        collapsingToolbarLayout.setTitle("AC" + contentId);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTitleText);

        adapter = new ContentFragmentAdapter(getSupportFragmentManager(), contentId);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem selectItem = menu.add(Menu.NONE, 1, Menu.NONE, "选择下载");
        MenuItem selectAll = menu.add(Menu.NONE, 2, Menu.NONE, "下载全部");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                MyUtils.showToastShort("select item");
                break;
            case 2:
                MyUtils.showToastShort("select all");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusConfig.getInstance().unregister(this);
    }

    @Subscribe
    public void initTitleImg(final ContentInfo.DataEntity.FullContentEntity fullContentEntity) {
//        Log.i("contentActivity", fullContentEntity.getContentId());
        titleImg.setImageURI(fullContentEntity.getCover());
        titleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity =
                        fullContentEntity.getVideos().get(0);
                //播放第一个video
                MyUtils.showToastShort(videosEntity.getVideoId());
            }
        });
    }

    private void setCheckBoxShow(boolean isShowCheckBox, boolean isSelectAll) {

    }
}
