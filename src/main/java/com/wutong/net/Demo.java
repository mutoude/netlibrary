package com.wutong.net;

import com.wutong.net.base.BaseHttpResult;
import com.wutong.net.bean.GetPublicKeyMapBean;
import com.wutong.net.controler.HttControler;
import com.wutong.net.http.Http;
import com.wutong.net.inter.NetCallBack;
import com.wutong.net.model.NameValuePair;
import com.wutong.net.utils.ParamasUtils;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by CXL on 2018/7/14.
 */

public class Demo {

    private HttControler httControler;

    public void getData() {
        //httControler = new HttControler();
        Observable<BaseHttpResult<GetPublicKeyMapBean>> getPublicKeyMapObserable = Http.getHttpService().getPublicKeyMapBean(ParamasUtils.parseRequest(new ArrayList<NameValuePair>()));
        httControler.PostData(getPublicKeyMapObserable, new NetCallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailed(Throwable t) {

            }
        }, false);
    }

    public void onDestory() {
        if (httControler != null) {
            httControler.StopNet();
        }
    }

}
