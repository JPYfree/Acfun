package me.jpyfree.acfun.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.adapter.PartitionFragmentAdapter;
import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.base.BaseActivity;
import me.jpyfree.acfun.fragment.PartitionOtherFragment;
import me.jpyfree.acfun.utils.MyViewUtils;

/**
 * Created by Administrator on 2016/6/23.
 */
public class PartitionActivity extends BaseActivity {

    public static void startActivity(Context context, String partitionType) {
        Intent intent = new Intent(context, PartitionActivity.class);
        intent.putExtra(AcfunString.CHANNEL_IDS, partitionType);
        context.startActivity(intent);
    }

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String partitionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partition);

        toolbar = (Toolbar) findViewById(R.id.partition_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.partition_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.partition_viewpager);

        //根据partitionType判断加载哪个分区
        partitionType = getIntent().getStringExtra(AcfunString.CHANNEL_IDS);

        MyViewUtils.setToolbar(PartitionActivity.this, toolbar, true, partitionType);

        PartitionFragmentAdapter adapter = new PartitionFragmentAdapter(getSupportFragmentManager(),
                partitionType);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        if (partitionType.equals(AcfunString.TITLE_HOT)) {
            tabLayout.setVisibility(View.GONE);
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setHttpOrder(String order, String title) {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AcfunString.ORDER_BY, order);
        editor.putString(AcfunString.TITLE, title);
        editor.apply();

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.getUserVisibleHint() && fragment instanceof PartitionOtherFragment) {
                ((PartitionOtherFragment) fragment).setHttpOrder();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!partitionType.equals(AcfunString.TITLE_HOT) &&
                !partitionType.equals(AcfunString.TITLE_RANKING)) {
            MenuItem timeOrderItem = menu.add(Menu.NONE, 1, Menu.NONE, AcfunString.TITLE_TIME_ORDER);
            MenuItem lastPostItem = menu.add(Menu.NONE, 2, Menu.NONE, AcfunString.TITLE_LAST_POST);
            MenuItem mostReplyItem = menu.add(Menu.NONE, 3, Menu.NONE, AcfunString.TITLE_MOST_REPLY);
            MenuItem popularityItem = menu.add(Menu.NONE, 4, Menu.NONE, AcfunString.TITLE_POPULARITY);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case 1:
                setHttpOrder(AcfunString.TIME_ORDER, AcfunString.TITLE_TIME_ORDER);
                break;
            case 2:
                setHttpOrder(AcfunString.LAST_POST, AcfunString.TITLE_LAST_POST);
                break;
            case 3:
                setHttpOrder(AcfunString.MOST_REPLY, AcfunString.TITLE_MOST_REPLY);
                break;
            case 4:
                setHttpOrder(AcfunString.POPULARITY, AcfunString.TITLE_POPULARITY);
        }
        return super.onOptionsItemSelected(item);
    }
}
