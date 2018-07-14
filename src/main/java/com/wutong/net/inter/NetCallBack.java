package com.wutong.net.inter;

/**
 * Created by CXL on 2018/7/14.
 */

public interface NetCallBack<T> {

    void onSuccess(T t);

    void onFailed(Throwable t);
}
