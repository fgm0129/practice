package com.practice.spring.bean;

import org.springframework.stereotype.Service;

/**
 * Created by fgm on 2017/7/16.
 */
@JobHandler(value = "helloJobBean")
@Service
public class HelloJobBean {

    private String jobId;


    public String execute(String param){

        return "success "+param;
    }

}
