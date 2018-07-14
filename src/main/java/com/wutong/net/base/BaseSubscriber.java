package com.wutong.net.base;


import com.wutong.net.exception.ApiException;

import rx.Subscriber;


/**
 * Created by CXL on 2018/7/10.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        ApiException apiException = (ApiException) e;
        onError(apiException);
    }


    /**
     * @param e 错误的一个回调
     */
    protected abstract void onError(ApiException e);

}
