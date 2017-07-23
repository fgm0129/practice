package com.practice.thread.queue;

import com.practice.thread.queue.impl.HelloJobHandler;

/**
 * Created by fgm on 2017/7/22.
 */
public class ThreadBootstrap{

    public static void main(String[] args) {


        JobThread jobThread=JobThreadExecutor.registerJobThread(1,new HelloJobHandler(),"测试任务执行");

        TriggerParam triggerParam1=new TriggerParam();
        triggerParam1.setJobId(1);
        triggerParam1.setLogId(1);
        triggerParam1.setExecutorParams("1,2,3");
        jobThread.pushTriggerQueue(triggerParam1);

        jobThread.toStop("测试任务中断!");
        TriggerParam triggerParam2=new TriggerParam();
        triggerParam2.setJobId(2);
        triggerParam2.setLogId(2);
        triggerParam2.setExecutorParams("4,5,6");
        jobThread.pushTriggerQueue(triggerParam2);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
