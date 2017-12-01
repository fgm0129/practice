package com.practice.rpc.test;

import com.practice.api.HelloService;
import com.practice.rpc.simple.RpcFramework;
import com.practice.rpc.test.impl.HelloServiceImpl;

/**
 * Created by fgm on 2017/7/23.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }


}
