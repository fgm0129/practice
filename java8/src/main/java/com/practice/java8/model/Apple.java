package com.practice.java8.model;

/**
 * Created by fgm on 2017/11/2.
 * @author fgm
 */
public class Apple extends Fruit{
    int id;

    public Apple(int weight, String color) {
        super(weight,"apple",color);
    }

    public Apple(int weight) {
        this(weight,"red");
    }

    public Apple(int id,int weight,String color) {
        super(weight,"apple",color);
        this.id=id;
    }

}
