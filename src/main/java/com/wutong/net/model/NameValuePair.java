package com.wutong.net.model;

/**
 * NameValuePair
 * Created by CXL on 2018/7/13.
 */

public class NameValuePair {
    public NameValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key = "";

    private String value = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
