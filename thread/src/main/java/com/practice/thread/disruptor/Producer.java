package com.practice.thread.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by fgm on 2017/7/9.
 */
public class Producer {

    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer byteBuffer){
        long sequence=ringBuffer.next();
        try{
            PCData pcData=ringBuffer.get(sequence);
            pcData.setValue(byteBuffer.getLong(0));
        }finally {
            ringBuffer.publish(sequence);
        }



    }
}
