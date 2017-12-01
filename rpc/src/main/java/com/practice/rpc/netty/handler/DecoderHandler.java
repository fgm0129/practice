package com.practice.rpc.netty.handler;

import com.practice.rpc.netty.ByteObjConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by fgm on 2017/11/30.
 */
public class DecoderHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if(byteBuf.readableBytes()==0){
            return;
        }
        byte[] data=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        Object obj = ByteObjConverter.byteToObject(data);
        list.add(obj);

    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {


        //super.channelReadComplete(ctx);
        ctx.channel().disconnect().sync();

    }



}
