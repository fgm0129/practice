package com.practice.thread.util;

import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by fgm on 2017/7/23.
 */
public class WebUtils {

    private static final String CODE="code";
    private static final String DATA="data";
    private static final String MESSAGE="message";


    public static ResponseEntity<Map<String,Object>> success(){
        Map<String,Object> map= Maps.newHashMap();
        map.put(CODE,Result.SUCCESS.getCode());
        Map<String,String> message=Maps.newHashMap();
        message.put(MESSAGE,Result.SUCCESS.getText());
        map.put(DATA,message);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String,Object>> result(Object object){
        Map<String,Object> map= Maps.newHashMap();
        map.put(CODE,Result.SUCCESS.getCode());
        map.put(DATA,object);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String,Object>> success(String message){
        Map<String,Object> map= Maps.newHashMap();
        map.put(CODE,Result.SUCCESS.getCode());
        Map<String,String> messageMap=Maps.newHashMap();
        messageMap.put(MESSAGE,message);
        map.put(DATA,message);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String,Object>> error(){
        Map<String,Object> map= Maps.newHashMap();
        map.put(CODE,Result.FAIL.getCode());
        Map<String,String> message=Maps.newHashMap();
        message.put(MESSAGE,Result.FAIL.getText());
        map.put(DATA,message);
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static ResponseEntity<Map<String,Object>> error(String message){
        Map<String,Object> map= Maps.newHashMap();
        map.put(CODE,Result.FAIL.getCode());
        Map<String,String> messageMap=Maps.newHashMap();
        messageMap.put(MESSAGE,message);
        map.put(DATA,message);
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Map<String,Object>> error(Object data){
        Map<String,Object> map= Maps.newHashMap();
        map.put(CODE,Result.SUCCESS.getCode());
        map.put(DATA,data);
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
