package com.practice.thread.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.practice.hack.changelog.ChangeLogLevelProcessUnit;
import com.practice.thread.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by fgm on 2017/8/20.
 */
@RestController
@EnableAutoConfiguration
public class ChageLogController {

    Logger log= LoggerFactory.getLogger(ChageLogController.class);

    @Autowired
    private ChangeLogLevelProcessUnit changeLogLevel;

    @RequestMapping(value = "/changeLog", method = RequestMethod.GET)
    public  ResponseEntity<Map<String,Object>> changeLog(@RequestParam("level") String level) {

        try {
            if(StringUtils.isEmpty(level)){
                return WebUtils.error("日志级别不能为空!");
            }
            Map<String,Object> requestMap= Maps.newHashMap();
            List<Map<String,String>> loggerList= Lists.newArrayList();
            Map<String,String> logger=Maps.newHashMap();
            loggerList.add(logger);
            logger.put("loggerName","root");
            logger.put("logLevel",level);
            requestMap.put("loggerList",loggerList);
            requestMap.put("opType","set");
            String gson=new Gson().toJson(requestMap,Map.class);
            log.info("请求参数:{}",gson);
            String result=changeLogLevel.invoke(gson);
            return WebUtils.success(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WebUtils.error("日志级别改变失败!");

    }

    @RequestMapping(value = "/getLogLevel", method = RequestMethod.GET)
    public  ResponseEntity<Map<String,Object>> getLogLevel() {
        try {
            Map<String,Object> requestMap= Maps.newHashMap();
            requestMap.put("opType","get");
            String gson=new Gson().toJson(requestMap);
            log.info("请求参数:{}",gson);
            String result=changeLogLevel.invoke(gson);
            return WebUtils.result(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WebUtils.error("日志级别改变失败!");
    }


    @RequestMapping(value = "/logTest", method = RequestMethod.GET)
    public  ResponseEntity<Map<String,Object>> logTest() {
        try {
            log.debug("debug 级别日志");
            log.info("info 级别日志");
            log.error("error 级别日志");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return WebUtils.success();
    }





}
