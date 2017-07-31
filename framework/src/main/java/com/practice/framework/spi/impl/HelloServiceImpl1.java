package com.practice.framework.spi.impl;

import com.practice.framework.spi.HelloService;

/**
 * Created by fgm on 2017/7/29.
 */
public class HelloServiceImpl1 implements HelloService {
    public void sayHello(String name) {

        System.out.println("Hello "+name +" @version 1");
    }
}
