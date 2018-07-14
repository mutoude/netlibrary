package com.wutong.net.controler;

import android.app.ProgressDialog;
import android.content.Context;

import com.wutong.net.NetApp;
import com.wutong.net.base.BaseHttpResult;
import com.wutong.net.bean.GetPublicKeyMapBean;
import com.wutong.net.inter.NetCallBack;
import com.wutong.net.subscriber.CommonSubscriber;
import com.wutong.net.transformer.CommonTransformer;

import java.lang.ref.WeakReference;

import rx.Observable;

/**
 * HttControler  写法一
 * Created by CXL on 2018/7/14.
 */

public class HttControler<T> {

    public HttControler(Context context) {
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载中，请稍等......");
        }
    }

    private CommonSubscriber<T> subscriber;

    private ProgressDialog progressDialog;

    public void PostData(rx.Observable<BaseHttpResult<T>> observable, final NetCallBack<T> netCallBack, boolean isCancleAble) {
        if(subscriber != null) {
            subscriber.unsubscribe();
        }
        if (progressDialog != null) {
            if(!isCancleAble) {
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }
            progressDialog.show();
        }
        subscriber = new CommonSubscriber<T>(NetApp.getApplicationContext(), netCallBack) {
            @Override
            public void onNext(T t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (netCallBack != null) {
                    netCallBack.onSuccess(t);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (netCallBack != null) {
                    netCallBack.onFailed(e);
                }
            }
        };

        observable.compose(new CommonTransformer<T>())
                .subscribe(subscriber);
    }

    public void StopNet() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
    }
}
