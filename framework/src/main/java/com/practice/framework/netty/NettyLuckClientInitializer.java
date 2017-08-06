package com.practice.framework.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by fgm on 2017/7/26.
 */
public class NettyLuckClientInitializer extends ChannelInitializer<SocketChannel> {
    private static final LuckEncoder ENCODER = new LuckEncoder();


    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // 添加编解码器, 由于ByteToMessageDecoder的子类无法使用@Sharable注解,
        // 这里必须给每个Handler都添加一个独立的Decoder.
        pipeline.addLast(ENCODER);
        pipeline.addLast(new LuckDecoder());
        // and then business logic.
        pipeline.addLast(new NettyLuckClientHandler());
    }
}
