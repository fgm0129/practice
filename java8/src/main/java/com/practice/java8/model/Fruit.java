package com.practice.java8.model;

/**
 * Created by fgm on 2017/11/4.
 */
public class Fruit {
    private Integer weight;
    private String name;
    private String color;

    public Fruit(Integer weight) {
        this.weight = weight;
    }

    public Fruit(Integer weight,String name) {
        this.name = name;
        this.weight = weight;
    }

    public Fruit(Integer weight, String name, String color) {
        this.weight = weight;
        this.name = name;
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
