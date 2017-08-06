package com.practice.thread.queue.core;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by fgm on 2017/8/4.
 */
public class QueueTest {

    final static ArrayBlockingQueue<Integer> arrayBlockingQueue=new ArrayBlockingQueue(5);



    public static void main(String[] args) {

        Thread putThread=new Thread(new BlockQueueHandler(1,arrayBlockingQueue));
        Thread getThread=new Thread(new BlockQueueHandler(0,arrayBlockingQueue));

        putThread.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getThread.start();




    }


}
