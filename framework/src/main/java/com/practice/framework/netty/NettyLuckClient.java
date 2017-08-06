package com.practice.framework.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Created by fgm on 2017/7/26.
 */
public class NettyLuckClient {

    public static void main(String[] args) throws InterruptedException {


        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1",8888))
                    .handler(new NettyLuckClientInitializer());


            ChannelFuture future= b.connect().sync();
            future.channel().closeFuture().sync();


        } finally {
            group.shutdownGracefully();
        }




    }

}
