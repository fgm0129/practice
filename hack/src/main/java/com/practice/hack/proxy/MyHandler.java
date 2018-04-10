package com.practice.hack.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author fgm
 * @date 2018/3/21
 * @description
 */
public class MyHandler implements InvocationHandler {
    private Object target;

    public MyHandler(Object target){
        this.target=target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke service");
        return method.invoke(target,args);
    }
}
