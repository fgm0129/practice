package com.practice.java8.charter14;


/**
 * Created by fgm on 2017/11/28.
 */
public class MyLinkedList<T> implements MyList<T> {
    private final T head;
    private final MyList<T> tail;

    public MyLinkedList(T head,MyList<T> tail){
        this.head=head;
        this.tail=tail;
    }
    @Override
    public T head() {
        return head;
    }

    @Override
    public MyList<T> tail() {
        return tail;
    }
    @Override
    public boolean isEmpty(){
        return false;
    }
}
