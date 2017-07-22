package com.practice.hack.groovy;

import com.practice.base.SpringBootstrapBase;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by fgm on 2017/7/22.
 * 根据
 *
 *
 */
@Slf4j
public class GroovyFactory {

    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();


    private static final GroovyFactory instance=new GroovyFactory();

    public static GroovyFactory getInstance(){
        return instance;
    }

    /**
     * @description 实例注入
     */
    private  void inject(Object  instance){
        if(instance==null){
            return;
        }
        Field[] fields = instance.getClass().getDeclaredFields();
        for(Field field:fields){
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            Object fieldBean=null;
            if(AnnotationUtils.getAnnotation(field,Resource.class)!=null){
                Resource resource = AnnotationUtils.getAnnotation(field, Resource.class);
                if(!StringUtils.isEmpty(resource.name())){
                    fieldBean=SpringBootstrapBase.getContext().getBean(resource.name());
                }else{
                    fieldBean=SpringBootstrapBase.getContext().getBean(field.getName());
                }
            }else if(AnnotationUtils.getAnnotation(field,Autowired.class)!=null){
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class);
                if(qualifier!=null &&!StringUtils.isEmpty(qualifier.value())){
                    fieldBean=SpringBootstrapBase.getContext().getBean(qualifier.value());
                }else{
                    fieldBean=SpringBootstrapBase.getContext().getBean(field.getType());
                }
            }

            if(fieldBean!=null){
                field.setAccessible(true);
                try {
                    field.set(instance,fieldBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }








    }

    public IJobHandler loadJobHandler(String codeSource) throws Exception{

        if(StringUtils.isEmpty(codeSource)||StringUtils.isEmpty(codeSource.trim())){
           throw  new Exception("load instance error,codeSource is empty!");
        }
        Class<?> clazz=groovyClassLoader.parseClass(codeSource);
        if(clazz==null){
            throw new Exception("load instance error,class is null!");
        }
        Object instance=clazz.newInstance();
        if(instance==null){
            throw new Exception("load instance error,instance is null!");
        }
        if(!(instance instanceof IJobHandler)){
            throw new Exception("load instance error,cannot convert from instance["+instance.getClass()+"] to IJobHandler");
        }
        this.inject(instance);
        return (IJobHandler)instance;

    }





}
