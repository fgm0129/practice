package com.practice.hack.proxy;

/**
 * @author fgm
 * @date 2018/3/21
 * @description
 */
public class HelloServiceImpl implements HelloService {

    @Override
      public String hello(String name) {
        return "hello "+name;
    }
}
