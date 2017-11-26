package com.practice.java8;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by fgm on 2017/11/6.
 * @author fgm
 */
public class StreamTest {

    private static Gson gson=new Gson();


    private static List<String> list= Lists.newArrayList("A","b","c","D","E","f");

    private static List<Integer> list1=Lists.newArrayList(1,2,3,4,5);

    private static List<Integer> list2=Lists.newArrayList(2,3);


    public static void main(String[] args) {

        List<String> result= list.stream().map(s->s.toUpperCase()).collect(Collectors.toList());
        System.out.println(gson.toJson(list));
        System.out.println(gson.toJson(result));

        //计算平方
        List<Integer> resultList1=list1.stream().map(d->d*d).collect(Collectors.toList());
        System.out.println(gson.toJson(resultList1));

        //flatMap
        List<int[]> resultList2=list1.stream().flatMap(i->list2.stream().map(j->new int[]{i,j})).collect(Collectors.toList());
        System.out.println(gson.toJson(resultList2));

        //flatMap2
        List<int[]> resultList3=list1.stream()
                      .flatMap(i->list2.stream()
                                      .filter(j->(i+j)%3==0)
                                      .map(j->new int[]{i,j})
                        )
                      .collect(Collectors.toList());

        System.out.println(gson.toJson(resultList3));

        //findFirst
        Optional<Integer> value=list1.stream().map(d->d*d).filter(x->x%3==0).findFirst();
        System.out.println(value.get());

        //reduce 归约
        Integer sum1=list1.stream().reduce(0,(a,b)->a+b);
        Integer sum2=list1.stream().reduce(0,Integer::sum);

        System.out.println(sum1+","+sum2);




    }

}
