package com.practice.framework.hprose;

import hprose.client.HproseTcpClient;

/**
 * Created by fgm on 2017/9/11.
 */
public class TCPHelloClient {

    interface IHello {
        String hello(String name);
        String execute(int num1,int num2);
    }

    public static void main(String[] args) {

        System.out.println("START");

        HproseTcpClient client=new HproseTcpClient("tcp://localhost:4321");
        IHello helloClient=client.useService(IHello.class);
        System.out.println(helloClient.hello("World"));
        System.out.println(helloClient.execute(1,3));

        System.out.println("END");


    }



}
