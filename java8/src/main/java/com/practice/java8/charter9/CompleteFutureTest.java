package com.practice.java8.charter9;


import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by fgm on 2017/11/27.
 */
public class CompleteFutureTest {

    static Random random=new Random();



    public static void testCompletableFuture() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture=new CompletableFuture();

        Thread thread=new Thread(()->{
            System.out.println("task doing...");
            try{
                Thread.sleep(1000);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            System.out.println("task done");
            completableFuture.complete("finished");

        });
        thread.start();

       String result=completableFuture.get();

        System.out.println("执行结果："+result);

    }

    public static void testCompletableFutureException() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture=new CompletableFuture();
        Thread thread=new Thread(()->{
            try{
                Thread.sleep(1000);
                throw new RuntimeException("抛异常了！");
            }catch (Exception ex){
                completableFuture.completeExceptionally(ex);
            }

        });
        thread.start();
        String result=completableFuture.get();

        System.out.println("执行结果："+result);

    }

    public static void testSupplyASync() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result";
        });
        String result=completableFuture.get();


        System.out.println("执行结果："+result);

    }


    public static void testAllOfAndAnyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1=CompletableFuture.supplyAsync(()->{

            try{
                Thread.sleep(1500);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            System.out.println("finished 1 ");
            return "completableFuture1";
        });

        CompletableFuture<String> completableFuture2=CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(1000);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            System.out.println("finished 2 ");
            return "completableFuture2";
        });


        //两个任务都要完成才能结束
        CompletableFuture<Void> allResult=CompletableFuture.allOf(completableFuture1,completableFuture2);
        allResult.join();

        //任一个任务结束就能返回
        CompletableFuture<Object> anyResult=CompletableFuture.anyOf(completableFuture1,completableFuture2);
        System.out.println("第一个完成任务的返回结果"+anyResult.get());



    }


    /**
     * @description
     * thenCompose 方法允许你对两个异步操作进行流水线，
     * 第一个操作完成时，将其结果作为参数传递给第二个操作
     */
    public static void testThenCompose() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completedFuture1=CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
        CompletableFuture<String> completableFuture2=completedFuture1
                .thenCompose(result->CompletableFuture.supplyAsync(()->result+" world"));
        String result=completableFuture2.get();
        System.out.println("compose return:"+result);


    }

    /**
     * @description
     * 你需要将两个完  全不相干的 CompletableFuture 对象的结果整合起来，
     * 而且你也不希望等到第一个任务完全结束才开始第二项任务.
     *
     */
    public static void testThenCombine() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture1=CompletableFuture.supplyAsync(()->{
            System.out.println("in completableFuture1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("finished completableFuture1");
            return "Hello";
        });

        CompletableFuture<String> completableFuture2=completableFuture1
                .thenCombine(CompletableFuture.supplyAsync(()-> {
                    System.out.println("in completableFuture2");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("finished completableFuture2");
                    return " World";
                }),(result1,result2)->result1+result2);

        System.out.println(completableFuture2.get());



    }


    public static void  testThenAccept(){

        CompletableFuture<String> completabledFuture1=CompletableFuture.supplyAsync(()->{
            System.out.println("in completabledFuture1");
            return "Hello";
        });
        completabledFuture1.thenAccept(result-> System.out.println("执行结果："+result));





    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // testCompletableFuture();

        //testCompletableFutureException();
        //testSupplyASync();
        //testAllOfAndAnyOf();
       // testThenCompose();
       // testThenCombine();
        testThenCombine();


    }




    public static void thenAcceptBoth(){

        CompletableFuture.supplyAsync(()->{
            System.out.println("in hello");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("return hello");
            return "Hello";
        }).thenAcceptBoth(
                CompletableFuture.supplyAsync(()->{
                    System.out.println("return world");
                    return "world";
                }),
                (s1,s2)-> System.out.println(s1+" "+s2)
        );




    }






}
