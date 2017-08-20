package com.practice.framework.netty.embed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by fgm on 2017/8/12.
 */
public class FixLengthFrameDecodeTest {

    @Test
    public void testFramesTest(){
        ByteBuf buf= Unpooled.buffer();
        for(int i=0;i<9;i++){
          buf.writeByte(i);
        }
        ByteBuf input=buf.duplicate();

        EmbeddedChannel channel=new EmbeddedChannel(new FixedLengthFrameDecode(3));
        Assert.assertTrue(channel.writeInbound(input.retain()));

        Assert.assertTrue(channel.finish());

        ByteBuf read=(ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3),read);
        read.release();

        read=(ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3),read);
        read.release();

        read=(ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3),read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();



    }


}
