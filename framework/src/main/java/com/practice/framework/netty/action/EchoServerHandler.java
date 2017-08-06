package com.practice.framework.netty.action;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by fgm on 2017/8/6.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    public void channelRead(ChannelHandlerContext ctx,Object msg){

        ByteBuf in=(ByteBuf)msg;
        System.out.println("Server received:" + in.toString(CharsetUtil.UTF_8));
        ByteBuf out=Unpooled.copiedBuffer("Hello I received message: "+in.toString(CharsetUtil.UTF_8),CharsetUtil.UTF_8);
        ctx.write(out);


    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }




}
