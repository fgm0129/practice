package com.practice.java8.charter14;

/**
 * Created by fgm on 2017/11/28.
 */
public class EmptyList<T> implements MyList<T> {
    @Override
    public T head() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> tail() {
        throw new UnsupportedOperationException();
    }
}
