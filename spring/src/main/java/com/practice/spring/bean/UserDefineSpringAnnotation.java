package com.practice.spring.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fgm on 2017/7/16.
 * spring处理自定义注解的注解
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
