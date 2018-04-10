package com.practice.algorithm.sort;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fgm
 * @date 2018/3/18
 * @description
 *  LRU(Least Recently Used) 【最近最久未使用】
 *  当缓存满了，会最先淘汰【最近最久未使用】的值
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> implements Map<K,V>{
    private int maxSize;
    private final static float loadFactor=0.75f;
    private Lock lock=new ReentrantLock();


    public LRUCache(int initSize, int maxSize){
        //true,所有的Entry按照访问的顺序排列,正是这一点实现了LRU功能的
        //false,所有的Entry按照插入的顺序排列
        super(initSize,loadFactor,true);
        this.maxSize=maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        if(this.size()>maxSize){
            return true;
        }

        return false;
    }

    public K getElderLeastKey(){
        lock.lock();
        try{
            return this.entrySet().iterator().next().getKey();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public static float getLoadFactor() {
        return loadFactor;
    }

    @Override
    public V get(Object key) {
        lock.lock();
        try{
            return super.get(key);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;

    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try{
            return super.put(key, value);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;

    }
}
