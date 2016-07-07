package me.jpyfree.acfun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.fragment.PartitionHotFragment;
import me.jpyfree.acfun.fragment.PartitionOtherFragment;
import me.jpyfree.acfun.fragment.PartitionRankFragment;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PartitionFragmentAdapter extends FragmentStatePagerAdapter {

    private String partitionType;

    public PartitionFragmentAdapter(FragmentManager fm, String partitionType) {
        super(fm);
        this.partitionType = partitionType;
    }

    @Override
    public Fragment getItem(int position) {
        switch (partitionType) {
            case AcfunString.TITLE_HOT:
                return getHotFragment(position);
            case AcfunString.TITLE_RANKING:
                return getRankFragment(position);
            case AcfunString.TITLE_ANIMATION:
                return getAnimationFragment(position);
            case AcfunString.TITLE_FUN:
                return getFunFragment(position);
            case AcfunString.TITLE_MUSIC:
                return getMusicFragment(position);
            case AcfunString.TITLE_GAME:
                return getGameFragment(position);
            case AcfunString.TITLE_SCIENCE:
                return getScienceFragment(position);
            case AcfunString.TITLE_SPORT:
                return getSportFragment(position);
            case AcfunString.TITLE_TV:
                return getTvFragment(position);
            case AcfunString.TITLE_ESSAY:
                return getEssayFragment(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        switch (partitionType) {
            case AcfunString.TITLE_HOT:
                return 1;
            case AcfunString.TITLE_RANKING:
                return AcfunString.RANKING_TITLES.length;
            case AcfunString.TITLE_ANIMATION:
                return AcfunString.ANIMATION_TITLES.length;
            case AcfunString.TITLE_FUN:
                return AcfunString.FUN_TITLES.length;
            case AcfunString.TITLE_MUSIC:
                return AcfunString.MUSIC_TITLES.length;
            case AcfunString.TITLE_GAME:
                return AcfunString.GAME_TITLES.length;
            case AcfunString.TITLE_SCIENCE:
                return AcfunString.SCIENCE_TITLES.length;
            case AcfunString.TITLE_SPORT:
                return AcfunString.SPORT_TITLES.length;
            case AcfunString.TITLE_TV:
                return AcfunString.TV_TITLES.length;
            case AcfunString.TITLE_ESSAY:
                return AcfunString.ESSAY_TITLES.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (partitionType) {
            case AcfunString.TITLE_HOT:
                return null;
            case AcfunString.TITLE_RANKING:
                return AcfunString.RANKING_TITLES[position];
            case AcfunString.TITLE_ANIMATION:
                return AcfunString.ANIMATION_TITLES[position];
            case AcfunString.TITLE_FUN:
                return AcfunString.FUN_TITLES[position];
            case AcfunString.TITLE_MUSIC:
                return AcfunString.MUSIC_TITLES[position];
            case AcfunString.TITLE_GAME:
                return AcfunString.GAME_TITLES[position];
            case AcfunString.TITLE_SCIENCE:
                return AcfunString.SCIENCE_TITLES[position];
            case AcfunString.TITLE_SPORT:
                return AcfunString.SPORT_TITLES[position];
            case AcfunString.TITLE_TV:
                return AcfunString.TV_TITLES[position];
            case AcfunString.TITLE_ESSAY:
                return AcfunString.ESSAY_TITLES[position];
        }
        return null;
    }

    public Fragment getHotFragment(int position) {
        return PartitionHotFragment.newInstance();
    }

    public Fragment getRankFragment(int position) {
        return PartitionRankFragment.newInstance(AcfunString.RANKING_TITLES_ID[position]);
    }

    public Fragment getAnimationFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.ANIMATION_TITLES_ID[position]);
    }

    public Fragment getFunFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.FUN_TITLES_ID[position]);
    }

    public Fragment getMusicFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.MUSIC_TITLES_ID[position]);
    }

    public Fragment getGameFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.GAME_TITLES_ID[position]);
    }

    public Fragment getScienceFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.SCIENCE_TITLES_ID[position]);
    }

    public Fragment getSportFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.SPORT_TITLES_ID[position]);
    }

    public Fragment getTvFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.TV_TITLES_ID[position]);
    }

    public Fragment getEssayFragment(int position) {
        return PartitionOtherFragment.newInstance(AcfunString.ESSAY_TITLES_ID[position]);
    }
}
