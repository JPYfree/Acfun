package me.jpyfree.acfun.adapter.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.jpyfree.acfun.api.AcfunString;
import me.jpyfree.acfun.fragment.ContentInfoFragment;
import me.jpyfree.acfun.fragment.ContentReplyFragment;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ContentFragmentAdapter extends FragmentStatePagerAdapter {

    private String contentId;

    public ContentFragmentAdapter(FragmentManager fm, String contentId) {
        super(fm);
        this.contentId = contentId;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ContentInfoFragment.newInstance(contentId);
            case 1:
                return ContentReplyFragment.newInstance(contentId);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AcfunString.CONTENT_VIEW_PAGER_TITLE[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
