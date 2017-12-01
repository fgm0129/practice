package com.practice.java8.charter14;

/**
 * Created by fgm on 2017/11/28.
 */
public class MyTest {

    public static void main(String[] args) {


        MyList<Integer> l=new MyLinkedList<>(5,new MyLinkedList<>(10,new EmptyList()));

        System.out.println(l.head());
        System.out.println(l.tail());


    }
}
