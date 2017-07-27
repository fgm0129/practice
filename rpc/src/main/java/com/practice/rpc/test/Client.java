package com.practice.rpc.test;

import com.practice.rpc.simple.RpcFramework;

/**
 * Created by fgm on 2017/7/23.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        HelloService service =RpcFramework.refer(HelloService.class,"127.0.0.1",1234);
        String result=service.hello("fangguangming");
        System.out.println(result);


        service.execute("执行命令");
    }

}
