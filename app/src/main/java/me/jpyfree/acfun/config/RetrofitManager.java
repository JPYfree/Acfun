package me.jpyfree.acfun.config;

import me.jpyfree.acfun.api.AcfunString;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/6/22.
 */
public class RetrofitManager {

    private static Retrofit.Builder builder;

    private static Retrofit.Builder getInstance() {
        if (builder == null) {
            synchronized (RetrofitManager.class) {
                if (builder == null) {
                    builder = new Retrofit.Builder();
                }
            }
        }
        return builder;
    }

    private static Retrofit getRetrofit(String url) {
        return RetrofitManager.getInstance()
                //使用Gson作为数据转换器
                .addConverterFactory(GsonConverterFactory.create())
                //使用RxJava作为回调适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    public static Retrofit getAcfunApiServer() {
        return RetrofitManager.getRetrofit(AcfunString.URL_ACFUN_API_SERVER);
    }

    public static Retrofit getAcfunICao() {
        return RetrofitManager.getRetrofit(AcfunString.URL_ACFUN_ICAO);
    }

    public static Retrofit getAcfunTv() {
        return RetrofitManager.getRetrofit(AcfunString.URL_ACFUN_TV);
    }
}
