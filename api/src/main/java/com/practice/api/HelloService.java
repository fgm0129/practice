package com.practice.api;

import com.practice.api.dto.GreetDto;

/**
 * Created by fgm on 2017/12/1.
 */
public interface HelloService {

    String echo();

    String hello(String name);

    GreetDto greet(String name);


}
