package me.jpyfree.acfun.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import me.jpyfree.acfun.R;

/**
 * Created by Administrator on 2016/6/19.
 */
public class MyViewUtils {

    /**
     * Toolbar
     * */
    public static void setToolbar(AppCompatActivity context, Toolbar toolbar, Boolean withHomeButton){
        context.setSupportActionBar(toolbar);
        if (withHomeButton) {
            ActionBar actionBar = context.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * Toolbar 添加自定义标题
     * */
    public static void setToolbar(AppCompatActivity context, Toolbar toolbar, Boolean withHomeButton, String title) {
        if (title != null) {
            toolbar.setTitle(title);
        }
        context.setSupportActionBar(toolbar);
        if (withHomeButton) {
            ActionBar actionBar = context.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * 侧滑菜单
     * */
    public static void setDrawer(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout,
                toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.syncState();
        drawerLayout.setScrimColor(Color.parseColor("#80ffffff"));
        drawerLayout.addDrawerListener(drawerToggle);
    }

    /**
     * 下拉刷新组件颜色
     * */
    public static void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light,
                    android.R.color.holo_blue_bright);
        }
    }

    /**
     * 沉浸模式
     * */
    public static void toggleHideyBar(View decorView) {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = decorView.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("info", "Turning immersive mode mode off. ");
        } else {
            Log.i("info", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        decorView.setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }

    /**
     * 警告
     * */
    public static AlertDialog showAlertDialog(Activity activity, String title, String message,
                                              DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", listener)
                .setNegativeButton("取消", null)
                .show();
    }
}
