package com.practice.framework.netty.embed;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by fgm on 2017/8/12.
 */
public class FixedLengthFrameDecode extends ByteToMessageDecoder {

    private final int frameLength;

    public FixedLengthFrameDecode(int frameLength){
        if(frameLength<0){
            throw new IllegalArgumentException("frameLength must be a positive integer:"+frameLength);
        }
        this.frameLength=frameLength;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while(in.readableBytes()>=frameLength){
            ByteBuf buf=in.readBytes(frameLength);
            out.add(buf);
        }

    }
}
