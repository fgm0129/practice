package com.practice.framework.hprose;

import hprose.server.HproseTcpServer;


/**
 * Created by fgm on 2017/9/11.
 */
public class TCPHelloServer {

    public static String hello(String name){
        return "Hello "+name+" !";
    }
    public static int  execute(int a1,int a2){
        return a1+a2;
    }


    public static void main(String[] args) throws Exception {
        HproseTcpServer server=new HproseTcpServer("tcp://localhost:4321");
        server.add("hello",TCPHelloServer.class);
        server.add("execute",TCPHelloServer.class);
        server.start();
        System.out.println("START");
        System.in.read();
        server.stop();
        System.out.println("STOP");

    }

}
