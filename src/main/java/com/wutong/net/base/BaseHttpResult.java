package com.wutong.net.base;

/**
 * BaseHttpResult
 * Created by CXL on 2018/7/10.
 */

public class BaseHttpResult<T> {
    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "status=" + status +
                ", message='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
