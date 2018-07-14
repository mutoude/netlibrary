package com.wutong.net.exception;

/**
 * ApiException
 * Created by CXL on 2018/7/14.
 */

public class ApiException extends RuntimeException {

    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
