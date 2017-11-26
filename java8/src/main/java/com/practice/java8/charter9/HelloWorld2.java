package com.practice.java8.charter9;

/**
 * Created by fgm on 2017/11/23.
 */
public class HelloWorld2 implements A,B {

    @Override
    public void hello() {
        B.super.hello();
    }
}
