package com.practice.java8.charter9;

/**
 * Created by fgm on 2017/11/23.
 */
public interface A_ extends A {
    @Override
    default void hello(){
        System.out.println("Hello A_");
    }
}
