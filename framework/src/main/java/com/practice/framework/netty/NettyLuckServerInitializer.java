package com.practice.framework.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by fgm on 2017/7/26.
 */
public class NettyLuckServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final LuckEncoder ENCODER = new LuckEncoder();


    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channel=socketChannel.pipeline();
        channel.addLast(ENCODER);
        channel.addLast(new LuckDecoder());
        channel.addLast(new NettyLuckServerHandler());
    }
}
