package com.practice.rpc.netty.handler;

import com.practice.rpc.netty.ByteObjConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by fgm on 2017/11/30.
 */
public class EncoderHandler extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        byte[] data=ByteObjConverter.objectToByte(object);
        byteBuf.writeBytes(data);
        channelHandlerContext.flush();

    }

}
