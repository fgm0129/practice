package com.practice.framework.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by fgm on 2017/7/26.
 */
public class LuckDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {

        int version=in.readInt();
        int contentLength=in.readInt();
        byte []sessionByte=new byte[36];
        in.readBytes(sessionByte);
        String sessionId = new String(sessionByte);
        LuckHeader header = new LuckHeader(version, contentLength, sessionId);


        byte[] content = new byte[in.readableBytes()];
        in.readBytes(content);
        LuckMessage message = new LuckMessage(header, new String(content));
        list.add(message);

    }
}
