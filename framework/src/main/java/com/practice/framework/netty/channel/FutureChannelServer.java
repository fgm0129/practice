package com.practice.framework.netty.channel;

import com.practice.framework.netty.NettyLuckServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.Charset;

/**
 * Created by fgm on 2017/7/27.
 */
public class FutureChannelServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 指定socket的一些属性
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup);



        serverBootstrap.channel(NioServerSocketChannel.class)
                       .childHandler(new NettyLuckServerInitializer());

        Channel ch = serverBootstrap.bind(8888).sync().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    ByteBuf byteBuf = Unpooled.copiedBuffer("Hello kitty!".toCharArray(), Charset.defaultCharset());
                    future.channel().writeAndFlush(byteBuf);
                    System.out.println("operation complete");
                }
            }
        }).channel();
        System.out.println("future 是异步执行的,listen at port 8888");

        ch.closeFuture().sync();






    }





}
