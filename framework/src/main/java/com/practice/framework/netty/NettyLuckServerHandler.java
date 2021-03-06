package com.practice.framework.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by fgm on 2017/7/26.
 */
public class NettyLuckServerHandler extends SimpleChannelInboundHandler<LuckMessage> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LuckMessage luckMessage) throws Exception {
        System.out.println(luckMessage.toString());
        luckMessage.setContent("hello I have received message:"+luckMessage.getContent());
        channelHandlerContext.write(luckMessage);
        channelHandlerContext.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
}
