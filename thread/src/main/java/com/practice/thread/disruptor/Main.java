package com.practice.thread.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by fgm on 2017/7/9.
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Executor  executor= Executors.newCachedThreadPool();
        PCDataFactory pcDataFactory=new PCDataFactory();
        int bufferSize=1024;
        Disruptor<PCData> disruptor=new Disruptor<PCData>(pcDataFactory,bufferSize,executor, ProducerType.MULTI,new BlockingWaitStrategy());

        disruptor.handleEventsWithWorkerPool(new Consumer(),new Consumer(),new Consumer(),new Consumer());
        disruptor.start();

        RingBuffer<PCData> ringBuffer=disruptor.getRingBuffer();
        Producer producer=new Producer(ringBuffer);
        ByteBuffer bb=ByteBuffer.allocate(8);
        for(long l=0;true;l++){
            bb.putLong(0,l);
            producer.pushData(bb);
            Thread.sleep(1000);
            log.info("add data: {}",l);

        }



    }

}
