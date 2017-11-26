package com.practice.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.practice.java8.model.Apple;
import com.practice.java8.model.Dish;
import com.practice.java8.model.Fruit;
import com.practice.java8.model.Orange;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * Created by fgm on 2017/11/2.
 * @author fgm

 */
public class LambdaTest {
    private static List<Apple> appleList;
    private static String rootPath;
    private static Gson gson=new Gson();

    private static Map<String,Function<Integer,Fruit>> map;

    private static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH) );

    static{
        appleList= Lists.newArrayList(new Apple(10,"red"),new Apple(15,"green"),new Apple(20,"green"),new Apple(18,"red"));
        rootPath= LambdaTest.class.getResource("/").getPath();
        map= Maps.newHashMap();
        map.put("orange", Orange::new);
        map.put("apple",Apple::new);

    }

    public static void main(String[] args) throws IOException {



        List<String> lowCaloricDishesName = menu.stream().filter(d->d.getCalories()<400)
                .sorted(comparing(Dish::getCalories).reversed())
                .map(d->d.getName())
                .collect(Collectors.toList());

        System.out.println(gson.toJson(lowCaloricDishesName));



        List<String> lowCaloricDishesName2 = menu.stream()
                .filter(d->{
                    System.out.println("filter-->"+d.getName());
                    return d.getCalories()>400;}
                       )
                .sorted(comparing(Dish::getCalories))
                .map(d->{
                    System.out.println("map-->"+d.getName());
                    return d.getName();
                })
                .collect(Collectors.toList());










    }


    public static void  test4(){
        Fruit fruit1=getMyFruit("orange",3);
        Fruit fruit2=getMyFruit("apple",2);

        System.out.println(fruit1);
        System.out.println(fruit2);

        appleList.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });

        System.out.println(gson.toJson(appleList));

        appleList.sort((Apple a1,Apple a2)->a1.getWeight().compareTo(a2.getWeight()));

        System.out.println(gson.toJson(appleList));

        appleList.sort(comparing((Apple a)->a.getWeight()));

        System.out.println(gson.toJson(appleList));

        appleList.sort(comparing(Apple::getWeight));
        System.out.println(gson.toJson(appleList));

    }

    public static Fruit getMyFruit(String fruit,Integer weight){
        return map.get(fruit).apply(weight);

    }








    static List<Apple> appleMap(List<Integer> list,Function<Integer,Apple> function){
        List<Apple> appleList=Lists.newArrayList();
        for(Integer integer:list){
            appleList.add(function.apply(integer));
        }
        return appleList;
    }

    static List<Apple> appleMap2(String color,List<Integer> list,BiFunction<Integer,String,Apple> function){
        List<Apple> appleList=Lists.newArrayList();
        for(Integer integer:list){
            appleList.add(function.apply(integer,color));
        }
        return appleList;
    }


    public static void test2(){
        List<String> strList=Lists.newArrayList("b","B","A","a","C");
        strList.sort(String::compareToIgnoreCase);

        System.out.println(gson.toJson(strList));

        List<Integer> weights=Lists.newArrayList(9,8,7,4);
        List<Apple> appleList1=appleMap(weights,Apple::new);

        List<Apple> appleList2=appleMap2("green",weights,Apple::new);

        System.out.println(gson.toJson(appleList1));
        System.out.println(gson.toJson(appleList2));
    }

    public static void test1() throws IOException{
        Comparator<Apple> comparator=(a1,a2)->a1.getWeight().compareTo(a2.getWeight());
        appleList.sort(comparator);
        System.out.println(gson.toJson(appleList));


        String oneLine=processFile((BufferedReader br)->br.readLine());

        System.out.println(oneLine);

        String twoLine=processFile((BufferedReader br)->br.readLine()+ br.readLine());

        System.out.println(twoLine);

        List<Apple> resultList=filter(appleList,(Apple apple)->apple.getWeight()>=18);
        System.out.println(gson.toJson(resultList));


        List<Apple> resultList2=map(appleList,(Apple apple)->{apple.setColor("red");return apple;});

        System.out.println(gson.toJson(resultList2));

        System.out.println(gson.toJson(appleList));
    }




     static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList();
        for(T s: list){
            result.add(f.apply(s));
        }
        return result;
     }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T s: list){
            if(p.test(s)){
                results.add(s);
            }
        }
        return results;
    }


    static String processFile(BufferedReaderProcessor p) throws
            IOException {

        try  (
                BufferedReader br =
                        new BufferedReader(new FileReader(rootPath+"/data.txt"))
        ){ return p.process(br);}

//
//        long uniqueWords = 0;
//        try(Stream<String> lines =
//                    Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
//            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
//                    .distinct()
//                    .count();
//        }
//        catch(IOException e){
//        }

    }




}
