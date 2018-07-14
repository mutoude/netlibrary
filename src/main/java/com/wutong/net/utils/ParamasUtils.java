package com.wutong.net.utils;

import com.wutong.net.model.NameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;

/**
 * ParamasUtils   json提交
 * Created by CXL on 2018/7/14.
 */

public class ParamasUtils {

    public static RequestBody parseRequest(List<NameValuePair> nameValuePairList) {

        String requestJson = "{}";
        JSONObject jsonObject = new JSONObject();
        if (nameValuePairList == null) {
            requestJson = jsonObject.toString();
            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestJson);
        }

        if (nameValuePairList.size() == 0) {
            requestJson = jsonObject.toString();
            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestJson);
        }
        try {
            for (int i = 0; i < nameValuePairList.size(); i++) {
                NameValuePair nameValuePair = nameValuePairList.get(i);
                jsonObject.put(nameValuePair.getKey(), nameValuePair.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestJson);
    }
}
