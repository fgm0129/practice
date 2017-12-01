package com.practice.rpc.test.impl;

import com.practice.api.HelloService;
import com.practice.api.dto.GreetDto;
import com.practice.rpc.aspect.ServiceProvider;

import java.util.Date;

/**
 * Created by fgm on 2017/7/23.
 */
@ServiceProvider(name = "helloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String echo() {
        return "echo success!";
    }

    @Override
    public String hello(String name) {
        return "hello "+name;
    }

    @Override
    public GreetDto greet(String name) {
        GreetDto greetDto=new GreetDto();
        greetDto.setDate(new Date());
        greetDto.setGreetSentence("say hello to you partner!");
        greetDto.setName("fangguangming");
        return greetDto;
    }


}
