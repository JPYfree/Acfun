package me.jpyfree.acfun.base;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

import me.jpyfree.acfun.MyApplication;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class BaseFragment extends Fragment {

    public String TAG = getClass().getSimpleName();
    protected boolean isVisible;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
