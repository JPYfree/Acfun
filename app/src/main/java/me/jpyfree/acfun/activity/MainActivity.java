package me.jpyfree.acfun.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.adapter.MainFragmentAdapter;
import me.jpyfree.acfun.base.BaseActivity;
import me.jpyfree.acfun.utils.MyUtils;
import me.jpyfree.acfun.utils.MyViewUtils;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.fragment_main_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.fragment_main_tab_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        MyViewUtils.setToolbar(this, toolbar, true);
        MyViewUtils.setDrawer(this, drawerLayout, toolbar);
        setUpDrawerContent(navigationView);

        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                item.setChecked(true);
                                break;
                            case R.id.navigation_download:
                                //转到下载页面
                                MyUtils.showToastShort("TO DO");
                                break;
                            case R.id.navigation_about:
                                //app信息
                                MyUtils.showToastShort("TO DO");
                                break;
                            default:
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            MyUtils.showToastShort("settings");
        }

        return super.onOptionsItemSelected(item);
    }
}
