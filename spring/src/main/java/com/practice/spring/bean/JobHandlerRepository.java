package com.practice.spring.bean;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fgm on 2017/7/16.
 * 过滤指定自定义注解
 *
 */
@Slf4j
@Component
public class JobHandlerRepository implements ApplicationContextAware {


    public static final Map<String,Object> repository= Maps.newConcurrentMap();

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("设置spring context");
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(JobHandler.class);
        if(!serviceBeanMap.isEmpty()){
            for(String beanName:serviceBeanMap.keySet()){
                Object object=serviceBeanMap.get(beanName);
                Object oldObject=repository.put(beanName,object);
                log.info("register bean {}",beanName);
                if(oldObject!=null){
                    log.info("bean {} is replace by new Object!",beanName);
                }
            }
        }else{
            log.info("serviceBeanMap is empty!");
        }


    }
}
