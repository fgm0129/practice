package com.practice.framework.netty.action;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by fgm on 2017/8/6.
 */
public class EchoServer {
    private final int port;
    public EchoServer(int port){
        this.port=port;
    }


    public static void main(String[] args) throws Exception {
        args=new String[1];
        args[0]="9999";
        if(args.length!=1){
            System.err.println("Usage:"+EchoServer.class.getSimpleName()+"<Port>");
            return;
        }
        int port=Integer.parseInt(args[0]);
        new EchoServer(port).start();

    }

    public  void start() throws Exception {
        final EchoServerHandler serverHandler=new EchoServerHandler();
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f=b.bind().sync();
            f.channel().closeFuture().sync();
        }finally{
            group.shutdownGracefully().sync();
        }

    }


}
