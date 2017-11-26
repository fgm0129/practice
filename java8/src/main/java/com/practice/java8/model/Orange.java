package com.practice.java8.model;

/**
 * Created by fgm on 2017/11/4.
 * @author fgm
 */
public class Orange extends Fruit {


    public Orange(Integer weight) {
        this(weight,"yellow");
    }

    public Orange(Integer weight, String color) {
        super(weight, "orange",color);
    }

}
