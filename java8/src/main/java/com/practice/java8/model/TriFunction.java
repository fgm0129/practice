package com.practice.java8.model;

/**
 * Created by fgm on 2017/11/19.
 */
public interface TriFunction<T,U,V,R> {
    R apply(T t,U u,V v);
}
