package com.practice.rpc.core;

/**
 * Created by fgm on 2017/12/1.
 */
public class BizException extends RuntimeException {

    private String code;
    private String message;
    public BizException(){

    }
    public BizException(String message) {
        super(message);
        this.message=message;
    }
    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message=message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
