package com.practice.thread.queue;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by fgm on 2017/7/22.
 */
@Slf4j
public class JobThread extends Thread {

    private IJobHandler jobHandler;
    private boolean toStop=false;
    private boolean isRunning=false;
    private String stopReason;

    private LinkedBlockingQueue<TriggerParam> triggerQueue;
    private Set<Integer> triggerLogIdSet;


    public JobThread(IJobHandler jobHandler){
        this.jobHandler=jobHandler;
        this.triggerQueue=new LinkedBlockingQueue<TriggerParam>();
        this.triggerLogIdSet=Sets.newConcurrentHashSet();
    }

    public void toStop(String stopReason){
        this.toStop=true;
        this.stopReason=stopReason;
    }
    public boolean isRunning(){
        return isRunning;
    }
    public IJobHandler getHandler(){
        return jobHandler;
    }

    public String pushTriggerQueue(TriggerParam triggerParam){
        if (triggerLogIdSet.contains(triggerParam.getLogId())) {
            log.debug("repeat trigger job, logId:{}", triggerParam.getLogId());
            return "FAIL";
        }
        triggerLogIdSet.add(triggerParam.getLogId());
        triggerQueue.add(triggerParam);

        return "SUCCESS";
    }


    public void run(){
        while(!toStop){
            isRunning=false;
            String result=null;
            try {
               TriggerParam triggerParam=triggerQueue.poll(3L, TimeUnit.SECONDS);
                if(triggerParam==null){
                    log.info("trigger param is null,finished");
                    continue;
                }
                isRunning=true;
                 String[] handlerParams=null;
                 String executorParams=triggerParam.getExecutorParams();
                if(!StringUtils.isEmpty(executorParams)){
                    handlerParams= (String[])Arrays.asList(executorParams.trim().split(",")).toArray();
                }
                triggerLogIdSet.remove(triggerParam.getLogId());
                try{
                    result=jobHandler.handler(handlerParams);
                    if(StringUtils.isEmpty(result)){
                        result="FAIL";
                    }
                }catch (Exception ex){
                    log.info("execute fail {}",ExceptionUtils.getMessage(ex));
                    result="FAIL";
                }

            } catch (InterruptedException ex) {
                log.info("execute fail {}",ExceptionUtils.getMessage(ex));
            }

            if(!toStop){
                //任务回调、任务继续进行
                log.info("任务执行结果{},通知回调,任务继续执行!",result);



            }else{
                //任务回调、任务终止
                log.info("任务执行结果{},通知回调。后续任务终止执行,原因:{}!",result,stopReason);


            }

        }

        while(triggerQueue!=null &&triggerQueue.size()>0){
            TriggerParam triggerParam = triggerQueue.poll();
            if(triggerParam!=null){
                String  result="在队列中被终止!";
                log.info("logId{}对应任务尚未执行,原因{}",triggerParam.getLogId(),result);
                //任务结果回调


            }

        }
        log.info(">>>>>>>>>>>> JobThread stopped, hashCode:{}", Thread.currentThread());


    }




}
