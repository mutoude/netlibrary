package com.wutong.net.http;



import com.wutong.net.base.BaseHttpResult;
import com.wutong.net.bean.GetPublicKeyMapBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * HttpService
 * Created by CXL on 2018/7/14.
 */

public interface HttpService {
    //登录接口
    @POST("getPublicKeyMap")
    Observable<BaseHttpResult<GetPublicKeyMapBean>> getPublicKeyMapBean(@Body RequestBody route);
}
