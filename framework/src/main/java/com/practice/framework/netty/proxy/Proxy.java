package com.practice.framework.netty.proxy;

/**
 * Created by fgm on 2017/7/30.
 */
public interface Proxy {
     void start();
     void stop();
     void loadCache(String cacheFile);
}
