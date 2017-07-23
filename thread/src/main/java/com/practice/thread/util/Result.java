package com.practice.thread.util;

/**
 * Created by fgm on 2017/7/23.
 */
public enum Result {

    SUCCESS(1,"SUCCESS"),
    FAIL(0,"FAIL");

    private int code;
    private String text;

    Result(int code,String text){
        this.code=code;
        this.text=text;
    }
    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
