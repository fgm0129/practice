package com.practice.framework.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by fgm on 2017/9/20.
 */
public class Curator_Zookeeper {

    public static RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework=CuratorFrameworkFactory.newClient("127.0.0.1:2181",retryPolicy);
        curatorFramework.start();
        curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/my","Hello World".getBytes());


        InterProcessMutex lock=new InterProcessMutex(curatorFramework,"/my");
        if(lock.acquire(3000, TimeUnit.MILLISECONDS)){
            try{
                //do something
                System.out.println("获取到分布式锁");
            }finally {
                lock.release();
            }
        }



    }

}
