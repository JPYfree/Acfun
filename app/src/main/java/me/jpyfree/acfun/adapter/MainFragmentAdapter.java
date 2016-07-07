package me.jpyfree.acfun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.fragment.BangumiFragment;
import me.jpyfree.acfun.fragment.EssayFragment;
import me.jpyfree.acfun.fragment.NavigationFragment;
import me.jpyfree.acfun.fragment.RecommendFragment;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RecommendFragment.newInstance();
            case 1:
                return NavigationFragment.newInstance();
            case 2:
                return BangumiFragment.newInstance();
            case 3:
                return EssayFragment.newInstance(AcfunString.ESSAY);

        }
        return null;
    }

    @Override
    public int getCount() {
        return AcfunString.MAIN_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AcfunString.MAIN_TITLES[position];
    }
}
