package com.wutong.net.bean;

/**
 * Created by CXL on 2018/7/11.
 */

//测试用
public class GetPublicKeyMapBean {


    private String exponent;
    private int isForeceUpdate;
    private String modulus;
    private String updateUrl;

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public int getIsForeceUpdate() {
        return isForeceUpdate;
    }

    public void setIsForeceUpdate(int isForeceUpdate) {
        this.isForeceUpdate = isForeceUpdate;
    }

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }
}
