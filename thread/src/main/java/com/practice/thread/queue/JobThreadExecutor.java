package com.practice.thread.queue;


import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by fgm on 2017/7/22.
 */
@Slf4j
public class JobThreadExecutor {

    private static final Map<Integer,JobThread> JobThreadRepository= Maps.newConcurrentMap();


    public static JobThread registerJobThread(Integer jobId,IJobHandler handler,String removeReason){
        JobThread newJobThread=new JobThread(handler);
        newJobThread.start();
        JobThread oldJobThread=JobThreadRepository.put(jobId,newJobThread);
        if(oldJobThread!=null){
            oldJobThread.toStop(removeReason);
            oldJobThread.interrupt();
        }
        log.info("编号为{} 的job注册成功!",jobId);
        return newJobThread;

    }

    public static void removeJobThread(int jobId, String removeOldReason){
        JobThread oldJobThread = JobThreadRepository.remove(jobId);
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
        }
    }
    public static JobThread loadJobThread(int jobId){
        JobThread jobThread = JobThreadRepository.get(jobId);
        return jobThread;
    }




}
