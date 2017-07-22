package com.practice.spring.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fgm on 2017/7/16.
 *
 * 试验:通过spring bean的初始化,归类特定类型的bean
 * JobHandlerRepository 用于收集带特定注解的bean,可以用来处理特定类型的事件
 *
 *
 */
@Configuration
public class UserDefineSpringAnnotation {



    public static void main(String[] args) {

        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        context.start();
        JobHandlerRepository repository=(JobHandlerRepository)context.getBean("jobHandlerRepository");
        System.out.println("仓库中JobHandler类型的bean的个数:"+repository.repository.size());

    }



}
