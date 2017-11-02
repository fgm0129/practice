package com.practice.framework.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created by fgm on 2017/7/26.
 */
public class NettyLuckClientHandler extends SimpleChannelInboundHandler<LuckMessage> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LuckMessage luckMessage) throws Exception {
        System.out.println("received message:"+luckMessage.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
            int version = 1;
            String sessionId = UUID.randomUUID().toString();
            String content = "I'm the luck protocol,hello world ,hello lucky! the  content is to long to read finished,hahahhahahahahaha.";
            LuckHeader header = new LuckHeader(version, content.length(), sessionId);
            LuckMessage message = new LuckMessage(header, content);
            ctx.writeAndFlush(message);
    }


}
