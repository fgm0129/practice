package com.practice.algorithm.sort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by fgm on 2017/12/3.
 *
 * LRU (Latest Recently Used  按照最近最少使用规则取值)
 *
 */
public class LRUSelect {

    private static CountDownLatch countDownLatch=new CountDownLatch(3);


    public static void main(String[] args) throws InterruptedException {
        final LRUCache<String,String> lruCache=new LRUCache(8,4);
        lruCache.put("A","1");
        lruCache.put("B","2");
        lruCache.put("C","3");
        lruCache.put("D","4");
        lruCache.put("E","5");
        System.out.println(lruCache);


       Runnable runnable=new Runnable() {
           @Override
           public void run() {
               //循环取最近最少使用的值
               for(int i=0;i<4;i++){
                   String elderLestKey=lruCache.getElderLeastKey();
                   lruCache.get(elderLestKey);
                   System.out.println("getKey "+elderLestKey);
                   System.out.println(lruCache);
               }
               countDownLatch.countDown();
               System.out.println(Thread.currentThread()+" finished");
           }
       };


        Thread t1=new Thread(runnable);
        Thread t2=new Thread(runnable);
        Thread t3=new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
        countDownLatch.await();






    }





    public void testLinkedHashMap(){
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
