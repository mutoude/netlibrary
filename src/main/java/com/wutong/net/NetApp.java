package com.wutong.net;

import android.content.Context;

/**
 * Application初始化必须调用初始化init
 * Created by CXL on 2018/7/14.
 */

public class NetApp {

    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context;
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }
}
