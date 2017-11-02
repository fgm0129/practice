package com.practice.algorithm.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by fgm on 2017/9/24.
 */
public class CyclicBarrierWorker implements Runnable {

    private int id;
    private CyclicBarrier barrier;

    public CyclicBarrierWorker(int id, final CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }
    public void run() {
        try {
            System.out.println(id + " th people wait");
            barrier.await(); // 大家等待最后一个线程到达
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int num = 10;
       final CyclicBarrier barrier = new CyclicBarrier(num, new Runnable() {
            public void run() {
                System.out.println("go on together!");
            }
        });
        for (int i = 1; i <= num; i++) {
            new Thread(new CyclicBarrierWorker(i, barrier)).start();
        }
        System.out.println(barrier.getNumberWaiting());
        Thread.sleep(1000);
        System.out.println(barrier.getNumberWaiting());


    }






}
