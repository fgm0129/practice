package com.practice.hack.groovy;

/**
 * Created by fgm on 2017/7/22.
 */
public abstract class IJobHandler {
    protected static final String SUCCESS="SUCCESS";
    protected static final String FAIL="FAIL";

    protected abstract String execute(String... params) throws Exception;
}
