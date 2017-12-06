package com.practice.algorithm.sort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fgm on 2017/12/3.
 *
 * LRU (Latest Recently Used  按照最近最少使用规则取值)
 *
 */
public class LRUSelect {


    public static void main(String[] args) {

        //（1）false，所有的Entry按照插入的顺序排列
       // （2）true，所有的Entry按照访问的顺序排列(put,get)
        LinkedHashMap<String,String> lm=new LinkedHashMap<String, String>(16,0.75f,true);
        lm.put("111","111");
        lm.put("222","222");
        lm.put("333","333");
        lm.put("444","444");
        lm.get("111");
        System.out.println(lm);//{222=222, 333=333, 444=444, 111=111}
        lm.get("222");
        System.out.println(lm);//{333=333, 444=444, 111=111, 222=222}
        lm.get("333");
        System.out.println(lm);//{444=444, 111=111, 222=222, 333=333}
        List<Map.Entry<String,String>> list=new ArrayList(lm.entrySet());

        System.out.println(list.get(0));






    }


}
