package com.practice.algorithm.integer;

/**
 * @author fgm
 * @date 2018/3/31
 * @description
 */
public class HelloInteger {


    /**
     * IntegerCache 会缓存-128到127之间的数字，所以在这之前的值相等的数据，地址也相同 == 判断 和 equals 判断都为true
     * 超过这个范围的Integer会在内存中新建地址，所以值相等，地址不同，== 判断为false
     * 相同类型的数据之间，equals判断是通过只判断来完成的
     * 不同类型的数据之前，equals判断发现类型不同，直接返回false
     * @param args
     */
    public static void main(String[] args) {
        Integer a=1;
        Integer b=2;
        Integer c=3;
        Integer d=3;
        Integer e=321;
        Integer f=321;
        Long g=3L;

        System.out.println(c==d); //true
        System.out.println(e==f); //false
        System.out.println(c==(a+b));//true
        System.out.println(c.equals(a+b));//true
        System.out.println(g==(a+b));//true
        System.out.println(g.equals(a+b));//false


    }
}
