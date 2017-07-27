package com.practice.framework.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by fgm on 2017/7/26.
 */
@ChannelHandler.Sharable
public class LuckEncoder extends MessageToByteEncoder<LuckMessage> {
    protected void encode(ChannelHandlerContext ctx, LuckMessage luckMessage, ByteBuf out) throws Exception {
        LuckHeader header=luckMessage.getLuckHeader();
        out.writeInt(header.getVersion());
        out.writeInt(header.getContentLength());
        out.writeBytes(header.getSessionId().getBytes());
        out.writeBytes(luckMessage.getContent().getBytes());


    }
}
