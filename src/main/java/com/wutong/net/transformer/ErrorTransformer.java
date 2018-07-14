package com.wutong.net.transformer;


import com.wutong.net.base.BaseHttpResult;
import com.wutong.net.exception.ErrorType;
import com.wutong.net.exception.ExceptionEngine;
import com.wutong.net.exception.ServerException;
import com.wutong.net.utils.LogUtils;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by CXL on 2018/7/14.
 */

public class ErrorTransformer<T> implements Observable.Transformer<BaseHttpResult<T>, T> {

    private static ErrorTransformer errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<BaseHttpResult<T>> responseObservable) {

        return responseObservable.map(new Func1<BaseHttpResult<T>, T>() {
            @Override
            public T call(BaseHttpResult<T> httpResult) {

                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");

                LogUtils.e(TAG, httpResult.toString());

                if (httpResult.getStatus() != ErrorType.SUCCESS)
                    throw new ServerException(httpResult.getStatus(), httpResult.getMessage());
                return httpResult.getData();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //Exception处理
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });

    }

    /**
     * 单例
     */
    public static <T> ErrorTransformer<T> getInstance() {

        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;

    }
}
