package com.wutong.net.subscriber;

import android.content.Context;

import com.wutong.net.base.BaseSubscriber;
import com.wutong.net.exception.ApiException;
import com.wutong.net.inter.NetCallBack;
import com.wutong.net.utils.LogUtils;
import com.wutong.net.utils.NetworkUtil;


/**
 * CommonSubscriber
 * Created by CXL on 2018/7/14.
 */

public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    private Context context;

    private NetCallBack<T> netCallBack;

    public CommonSubscriber(Context context, NetCallBack<T> netCallBack) {
        this.context = context;
        this.netCallBack = netCallBack;
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onStart() {

        if (!NetworkUtil.isNetworkAvailable(context)) {
            LogUtils.e(TAG, "网络不可用");
            if (netCallBack != null) {
                ApiException throwable = new ApiException(new Throwable(), -1);
                throwable.message = "网络不可用";
                throwable.code = -1;
                netCallBack.onFailed(throwable);
            }
        } else {
            LogUtils.e(TAG, "网络可用");
        }
    }


    @Override
    protected void onError(ApiException e) {
        LogUtils.e(TAG, "错误信息为 " + "code:" + e.code + "   message:" + e.message);
        if (netCallBack != null) {
            netCallBack.onFailed(e);
        }
    }

    @Override
    public void onCompleted() {
        LogUtils.e(TAG, "成功了");
    }

}
