package com.practice.framework.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * Created by fgm on 2017/8/19.
 */
public class ChatServer {

    private final ChannelGroup channelGroup=new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group=new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address){
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));

        ChannelFuture future=bootstrap.bind(address);
        future.syncUninterruptibly();
        channel=future.channel();
        return future;
    }

    private ChannelInitializer<Channel> createInitializer(ChannelGroup channelGroup) {
        return new ChatServerInitializer(channelGroup);

    }


    public void destroy(){
        if(channel!=null){
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
        System.out.println("server shutdownGracefully ");

    }

    public static void main(String[] args) {


        int port = 9999;
        final ChatServer endpoint = new ChatServer();
        ChannelFuture future = endpoint.start(
                new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        System.out.println("server start on port: "+port);
        future.channel().closeFuture().syncUninterruptibly();
        System.out.println("server closed on port: "+port);






    }



}
