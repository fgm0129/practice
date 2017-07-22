package com.practice.base;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fgm on 2017/7/22.
 */
public class SpringBootstrapBase {

    private static ClassPathXmlApplicationContext context;
    static {
         context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
         context.start();
    }
    public static ClassPathXmlApplicationContext getContext() {
       return context;
    }

}
