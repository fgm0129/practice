package com.practice.hack.weakreference;

import com.google.common.collect.Maps;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author fgm
 * @date 2018/4/2
 * @description
 *
 * SoftReference 弱引用，在内存溢出之前，会被回收的对象
 * WeakReference 虚引用，在gc发生时，一定会被回收的对象
 *
 *
 *
 */
public class HelloWeakReference {

    private static  byte[] _1MB=new byte[1024*1024];

    private static Map<String,WeakReference<byte[]>> cache=Maps.newConcurrentMap();


    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<30;i++){
            cache.put(i+"",new WeakReference(_1MB));
        }

        Thread.sleep(10000);



    }







}
