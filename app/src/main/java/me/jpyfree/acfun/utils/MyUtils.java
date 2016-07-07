package me.jpyfree.acfun.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.jpyfree.acfun.MyApplication;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MyUtils {

    private static Toast toast;

    /**
     * 全局Toast
     * */
    private synchronized static void globalToast(Context context, String tip, int duration) {
        if (toast != null) {
            toast.setText(tip);
            toast.setDuration(duration);
        }else {
            toast = Toast.makeText(context, tip, duration);
        }
        toast.show();
    }

    /**
     * Toast.short
     * */
    public static void showToastShort(String tip) {
        if (TextUtils.isEmpty(tip)) {
            tip = "unknown error";
        }
        globalToast(MyApplication.getInstance().getApplicationContext(), tip, Toast.LENGTH_SHORT);
    }

    /**
     * Toast.long
     * */
    public static void showToastLong(String tip) {
        if (TextUtils.isEmpty(tip)) {
            tip = "unknown error";
        }
        globalToast(MyApplication.getInstance().getApplicationContext(), tip, Toast.LENGTH_LONG);
    }

    /**
     * 获取屏幕尺寸
     * */
    public static String getScreenWxH(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;

        return width + "x" + height;
    }

    /**
     * dp转px
     * */
    public static int dp2px(Context context, float dpValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, displayMetrics);
    }

    /**
     * 获取设备信息
     * */
    public static String getDeviceInfo(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();
        sb.append("\nDeviceId(IMEI) = " + manager.getDeviceId());
        sb.append("\nDeviceSoftwareVersion = " + manager.getDeviceSoftwareVersion());
        sb.append("\nLine1Number = " + manager.getLine1Number());
        sb.append("\nNetworkCountryIso = " + manager.getNetworkCountryIso());
        sb.append("\nNetworkOperator = " + manager.getNetworkOperator());
        sb.append("\nNetworkOperatorName = " + manager.getNetworkOperatorName());
        sb.append("\nNetworkType = " + manager.getNetworkType());
        sb.append("\nPhoneType = " + manager.getPhoneType());
        sb.append("\nSimCountryIso = " + manager.getSimCountryIso());
        sb.append("\nSimOperator = " + manager.getSimOperator());
        sb.append("\nSimOperatorName = " + manager.getSimOperatorName());
        sb.append("\nSimSerialNumber = " + manager.getSimSerialNumber());
        sb.append("\nSimState = " + manager.getSimState());
        sb.append("\nSubscriberId(IMSI) = " + manager.getSubscriberId());
        sb.append("\nVoiceMailNumber = " + manager.getVoiceMailNumber());

        return sb.toString();
    }

    /**
     * 获取设备ID
     * */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取系统时间
     * */
    public static String getDateToString(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getDateToStringMDHM(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * 判断是否有网络连接
     * */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    /**
     * 毫秒 转 h:m:s
     * */
    public static String getTimeFromMillisecond(long millisecond) {
        if (millisecond <= 0) {
            return "00:00";
        }
        long hour = (millisecond % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minute = (millisecond % (1000 * 60 * 60)) / (1000 * 60);
        long second = (millisecond % (1000 * 60)) / 1000;

        String hourStr = hour > 10 ? String.valueOf(hour) : "0" + String.valueOf(hour);
        String minuteStr = minute > 10 ? String.valueOf(minute) : "0" + String.valueOf(minute);
        String secondStr = second > 10 ? String.valueOf(second) : "0" + String.valueOf(second);

        if (hour > 0) {
            return hourStr + ":" + minuteStr + ":" + secondStr;
        }
        return minuteStr + ":" + secondStr;
    }

    /**
     * 检查activity是否存在
     * */
    public static boolean isActivityLive(Activity activity) {
        return activity != null && !activity.isDestroyed() && !activity.isFinishing();
    }

    /**
     * 检查fragment是否存在
     * */
    public static boolean isFragmentLive(Activity activity, Fragment fragment) {
        return fragment.getUserVisibleHint() && MyUtils.isActivityLive(activity);
    }
}
