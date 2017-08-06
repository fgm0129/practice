package com.practice.thread.queue.core;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by fgm on 2017/8/4.
 */
public class BlockQueueHandler implements Runnable{

    private  int   type;
    private ArrayBlockingQueue<Integer> arrayBlockingQueue;
    public  BlockQueueHandler(Integer type,ArrayBlockingQueue arrayBlockingQueue){
        this.type=type;
        this.arrayBlockingQueue=arrayBlockingQueue;
    }
    public void run() {
        if(type==0){
            while(true){
                try {
                    int a=  arrayBlockingQueue.take();
                    System.out.println("take num: "+a);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }else{
            try {
                for(int i=0;i<10;i++){
                    arrayBlockingQueue.put(i);
                    System.out.println("put num: "+i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
