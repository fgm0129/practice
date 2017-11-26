package com.practice.java8;

import java.util.stream.Stream;

/**
 * Created by fgm on 2017/11/22.
 */
public class Charter7 {


    public static void  testParallelSum(){

      long start=System.nanoTime();
      long s= Stream.iterate(1L,i->i+1)
                .limit(10000000)
                .parallel()
                .reduce(0L,Long::sum);
       long end=System.nanoTime();
        System.out.println("cost:"+(end-start)+"ms");

    }

    public static void  testNormalSum(){

        long start=System.nanoTime();
        long s= Stream.iterate(1L,i->i+1)
                .limit(10000000)
                .reduce(0L,Long::sum);
        long end=System.nanoTime();
        System.out.println("cost:"+(end-start)+"ms");

    }




    public static void main(String[] args) {

        //testParallelSum();//2381753658ms
       // testNormalSum();//253404857ms





        Hello hello=()->"123";
        Hello hello2=()->{return "123";};

        HelloABC helloABC=()->{  System.out.println("Hello"); };

        System.out.println(hello.hello()+","+hello2.hello());




    }

    public interface  Hello{
        String hello();
    }


    public interface  HelloABC{
        void  hello();
    }


}
