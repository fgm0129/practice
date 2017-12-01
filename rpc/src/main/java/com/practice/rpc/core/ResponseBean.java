package com.practice.rpc.core;

import java.io.Serializable;

/**
 * Created by fgm on 2017/11/30.
 */
public class ResponseBean implements Serializable {
    private long responseTime;
    private String retCode;
    private String retMessage;
    private Object result;
    public  static String SUCCESS="1";
    public  static String ERROR="0";

    public long getResponseTime() {
        return responseTime;
    }
    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
