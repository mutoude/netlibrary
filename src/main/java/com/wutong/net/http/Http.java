package com.wutong.net.http;


import com.wutong.net.NetApp;
import com.wutong.net.url.UrlHelper;
import com.wutong.net.utils.NetworkUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CXL on 2018/7/14.
 */

public class Http {

    private static HttpService httpService;
    private static volatile Retrofit retrofit;


    /**
     * retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static HttpService getHttpService() {
        if (httpService == null) {
            httpService = getRetrofit().create(HttpService.class);
        }
        return httpService;
    }


    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("phoneSystem", "")
                        .addQueryParameter("phoneModel", "")
                        .build();
                JSONObject jsonObject = new JSONObject();
                RequestBody body = originalRequest.body();

                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .header("token", "sssssd")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkAvailable(NetApp.getApplicationContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkAvailable(NetApp.getApplicationContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Http.class) {
                if (retrofit == null) {
                    //log拦截器
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient
                            .Builder()
                            .addInterceptor(addQueryParameterInterceptor())  //参数添加
                            .addInterceptor(addHeaderInterceptor()) // token过滤
                            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                            .connectTimeout(60l, TimeUnit.SECONDS)
                            .readTimeout(60l, TimeUnit.SECONDS)
                            .writeTimeout(60l, TimeUnit.SECONDS)
                            .build();

                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(UrlHelper.BASE_URL)
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

}
