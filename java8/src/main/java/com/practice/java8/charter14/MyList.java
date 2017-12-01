package com.practice.java8.charter14;

/**
 * Created by fgm on 2017/11/28.
 */
public interface MyList<T> {
    T head();
    MyList<T> tail();
    default boolean isEmpty(){
        return true;
    }

}
