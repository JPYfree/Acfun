package me.jpyfree.acfun;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/19.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    protected RefWatcher refWatcher;

    public static MyApplication getInstance() {
        return myApplication;
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private Handler handler = new Handler();
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        refWatcher = LeakCanary.install(myApplication);

        //fresco
        Fresco.initialize(myApplication);

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                arrayList.add("1");
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                arrayList.remove("1");
            }
        });
    }

    public void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
