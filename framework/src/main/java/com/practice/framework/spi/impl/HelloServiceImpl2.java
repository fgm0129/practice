package com.practice.framework.spi.impl;

import com.practice.framework.spi.HelloService;

/**
 * Created by fgm on 2017/7/29.
 */
public class HelloServiceImpl2 implements HelloService {
    public void sayHello(String name) {
        System.out.println("hello "+name +" @version 2");
    }
}
