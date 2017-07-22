package com.practice.hack.groovy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by fgm on 2017/7/22.
 * 业务执行代码
 *
 */
@Slf4j
@Component
public class BusinessExecutor {

    public void execute(String... params){
        log.info("business is start,request: {}",params);
        for(int i=1;i<=5;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            log.info("heart beat "+i);
        }
        log.info("business is  finished!");

    }

}
