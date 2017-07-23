package com.practice.thread.queue.impl;

import com.google.gson.Gson;
import com.practice.thread.queue.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by fgm on 2017/7/22.
 */
@Slf4j
@Component
public class HelloJobHandler implements IJobHandler {
    public String handler(String... params) throws InterruptedException {
        log.info("handler params :{}"+new Gson().toJson(params));
        for(int i=1;i<=5;i++){
            Thread.sleep(1000);
            log.info("heart beat {}",i);
        }
        return "SUCCESS";
    }
}
