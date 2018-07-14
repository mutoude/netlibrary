package com.wutong.net.transformer;


import com.wutong.net.base.BaseHttpResult;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CXL on 2018/7/14.
 */

public class CommonTransformer<T> implements Observable.Transformer<BaseHttpResult<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseHttpResult<T>> tansFormerObservable) {
        return tansFormerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformer.<T>getInstance());
    }
}

