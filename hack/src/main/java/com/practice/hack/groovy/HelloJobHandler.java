package com.practice.hack.groovy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fgm on 2017/7/22.
 *
 * the demo of sourceCode  提供源码的类,不直接执行
 */
public class HelloJobHandler extends IJobHandler {
    private static  Logger log= LoggerFactory.getLogger(HelloJobHandler.class);
    @Autowired
    private BusinessExecutor businessExecutor;
    @Override
    public String execute(String... params) throws Exception {
        log.info("before execute helloJobBean");
        businessExecutor.execute(params);
        log.info("after execute helloJobBean");
        return SUCCESS;
    }
}
