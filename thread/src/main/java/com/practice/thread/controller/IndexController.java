package com.practice.thread.controller;

import com.practice.thread.queue.JobThread;
import com.practice.thread.queue.JobThreadExecutor;
import com.practice.thread.queue.TriggerParam;
import com.practice.thread.queue.impl.HelloJobHandler;
import com.practice.thread.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by fgm on 2017/7/23.
 */
@RestController
@EnableAutoConfiguration
public class IndexController {

    @Autowired
    private HelloJobHandler helloJobHandler;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    String home() {
        return "Hello World!";
    }

    /**
     * @description 启动JOB
     */
    @RequestMapping(value="/registerJob",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> startJob(@RequestParam("id") Integer id,@RequestParam("reason") String reason){

        if(StringUtils.isEmpty(reason)){
            reason="正常任务启动";
        }
        if(id==null){
            return WebUtils.error("ID 不能为空!");
        }
        JobThreadExecutor.registerJobThread(id,helloJobHandler,reason);
        return WebUtils.success("JOB注册成功!");
    }


    /**
     * @description 添加任务
     */
    @RequestMapping(value="/addTask",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addTask(TriggerParam triggerParam){

        if(triggerParam==null||triggerParam.getJobId()==0){
            return WebUtils.error("job启动参数不完整!");
        }

        JobThread jobThread=JobThreadExecutor.loadJobThread(triggerParam.getJobId());
        if(jobThread==null){
            return WebUtils.error("ID为"+triggerParam.getJobId()+"的JOB不存在!");
        }
        jobThread.pushTriggerQueue(triggerParam);

        return WebUtils.success("任务添加成功!");
    }








}
