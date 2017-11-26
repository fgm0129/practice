package com.practice.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.Invokable;
import com.practice.java8.model.Apple;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by fgm on 2017/11/15.
 */
public class ListUtils{


    public static  Map  transformToMap(String propertity, List list) {
        if(list==null||list.isEmpty()){
            return Maps.newHashMap();
        }
        Map result=Maps.newHashMap();
        try {
            for(Object item:list){
                Class clazz= item.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for(Field filed:fields){
                    filed.setAccessible(true);
                   if(filed.getName().equals(propertity)){
                        Object key=filed.get(item);
                        result.put(key,item);
                   }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static void main(String[] args) {
        List<Apple> apppleList= Lists.newArrayList();
        apppleList.add(new Apple(1,2,"red"));
        apppleList.add(new Apple(2,4,"green"));
        apppleList.add(new Apple(3,3,"green"));
        apppleList.add(new Apple(4,5,"red"));



       Map<Integer,Apple> map=transformToMap("id",apppleList);

        System.out.println(map);


    }


}
