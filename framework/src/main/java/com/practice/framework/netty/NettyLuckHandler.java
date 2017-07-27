package com.practice.framework.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by fgm on 2017/7/26.
 */
public class NettyLuckHandler extends SimpleChannelInboundHandler<LuckMessage> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LuckMessage luckMessage) throws Exception {
        System.out.println(luckMessage.toString());
    }
}
