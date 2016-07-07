package me.jpyfree.acfun.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.leakcanary.RefWatcher;

import me.jpyfree.acfun.MyApplication;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/6/19.
 */
public class BaseActivity extends AppCompatActivity {

    public String TAG = getClass().getSimpleName();
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
        compositeSubscription.unsubscribe();
    }
}
