package com.practice.rpc.test.impl;

import com.practice.rpc.test.HelloService;

/**
 * Created by fgm on 2017/7/23.
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hello "+name;
    }

    public void execute(String cmd) {
        System.out.println("execute cmd success!");
    }
}
