package com.practice.hack.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author fgm
 * @date 2018/3/21
 * @description
 */
public class JdkProxy {


    public static void main(String[] args) {

        InvocationHandler handler=  new MyHandler(new HelloServiceImpl());

        HelloService helloService=(HelloService) Proxy.newProxyInstance(JdkProxy.class.getClassLoader(), new Class[]{HelloService.class},handler);

        System.out.println(helloService.hello("fangguangming"));
        System.out.println(helloService.getClass().toString());



    }

}
