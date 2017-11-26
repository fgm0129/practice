package com.practice.java8;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.practice.java8.model.Apple;
import com.practice.java8.model.Dish;
import com.practice.java8.model.TriFunction;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by fgm on 2017/11/19.
 */
public class Test2 {


    private static final Gson gson=new Gson();

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

    private static final String rootPath;

    static {
        rootPath= LambdaTest.class.getResource("/").getPath();

    }



    public static void testMathFunction(){
        Function<Integer,Integer> f=x->x=x+1;
        Function<Integer,Integer> g=x->x=x*2;

        Function<Integer,Integer> h1=f.andThen(g);//===>g(f(x))
        Function<Integer,Integer> h2=f.compose(g);//===>f(g(x))
        int a1=h1.apply(1);
        int a2=h2.apply(1);

        System.out.println(a1);//4
        System.out.println(a2);//3



    }

    public static void testDistinct(){
        List<Integer> dataList= Lists.newArrayList(1,2,2,3,3,6,6,4);
       //distinct 归约
        List<Integer> evenList=dataList.stream().filter(i->i%2==0).distinct().collect(Collectors.toList());
        System.out.println(gson.toJson(evenList));

        //跳过3个元素后归约
        List<Integer> skipEvenList=dataList.stream().skip(3).filter(i->i%2==0).distinct().collect(Collectors.toList());

        System.out.println(gson.toJson(skipEvenList));


    }

    /**
     * @description 扁平化流
     */
    public static void testFlatStream(){
        List<String> words=Lists.newArrayList("Hello","World");
        List<String[]> resut1=words.stream()
                .map(word->word.split(""))
                .distinct()
                .collect(Collectors.toList());

        System.out.println(gson.toJson(resut1));


       List<Stream<String>>  result2=words.stream()
                .map(word->word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

       List<List<String>> result22= result2.stream()
                .map(stream->stream.distinct().collect(Collectors.toList()))
                .collect(Collectors.toList());

        System.out.println(gson.toJson(result22));


        // flatMap  扁平化流，将多个流合并成一个流
        List<String> result3=words.stream()
                .map(word->word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(gson.toJson(result3));



        List<Integer> lists1=Lists.newArrayList(1,2,3);
        List<Integer> lists2=Lists.newArrayList(2,4);
        List<int []> result4=lists1.stream()
                //扁平化流把各个stream<int[]{}>转换成一个数组
                .flatMap(i->lists2.stream().map(j->new int[]{i,j}))
                .collect(Collectors.toList());
        System.out.println(gson.toJson(result4));


        //reduce 归约  需要反复使用集合中一些元素
        List<Integer> lists3=Lists.newArrayList(1,2,3,4,5);
        Integer sum=lists3.stream().reduce(0,(a,b)->a+b);
        System.out.println(sum);
   //考虑流中没有任何元素的情况。reduce操作无
   //法返回其和，因为它没有初始值。这就是为什么结果被包裹在一个Optional对象里，以表明和可能不存在。
        Optional<Integer> sumOptional=lists3.stream().reduce(Integer::sum);
        System.out.println(sumOptional.get());





    }


    public static void testThreeItemArrary(){
       Stream<double[]> pythagoreanTriples=IntStream.rangeClosed(1,100).boxed()
                .flatMap(a->IntStream.rangeClosed(a,100)
                        .mapToObj(b->new double[]{a,b,Math.sqrt(a*a+b*b)})
                        .filter(t->t[2]%1==0)
                );

       // pythagoreanTriples.forEach(t-> System.out.println(t[0]+","+t[1]+","+t[2]));


        pythagoreanTriples.limit(10).forEach(t-> System.out.println(t[0]+","+t[1]+","+t[2]));




    }

    public static void testFiles(){
        long uniqueWords = 0;
        try     (Stream<String> lines =
                                Files.lines(Paths.get(rootPath+"data.txt"), Charset.defaultCharset()) ){
                 uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        }
        catch(IOException e){
        }

    }

    //Stream API提供了两个静态方法来从函数生成流：Stream.iterate和Stream.generate。
    public static void testFibo(){
        Stream.iterate(new int[]{0,1},t->new int[]{t[1],t[1]+t[0]})
                .limit(10)
                .forEach(t-> System.out.println("("+t[0]+","+t[1]+")"));


    }

    public static  void testGroupBy(){
        Apple apple =new Apple(1,"red");
        Apple apple1 =new Apple(2,"red");
        Apple apple2 =new Apple(1,"green");
        Apple apple3 =new Apple(2,"green");


        List<Apple> appleList=Lists.newArrayList(apple,apple1,apple2,apple3);
        Map<String,List<Apple>> appleMap= appleList.stream().collect(Collectors.groupingBy(Apple::getColor));

        System.out.println(gson.toJson(appleMap));

        String colors=appleList.stream().map(d->d.getColor()).collect(Collectors.joining(","));

        System.out.println(colors);

       IntSummaryStatistics intSummaryStatistics=appleList.stream().collect(Collectors.summarizingInt(Apple::getWeight));

        System.out.println(intSummaryStatistics.getSum());
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getAverage());

    }

    public static void testCollectReducing(){

        Stream<Integer> stream=Arrays.asList(1,2,3,4,5,6).stream();

      List<Integer> numbers= stream
              .reduce(new ArrayList(),
                      (List<Integer> l,Integer e)->{l.add(e);return l;},
                      (List<Integer> l1,List<Integer> l2)->{
                          l1.addAll(l2);
                          return l1;
                      }
              );
        System.out.println(gson.toJson(numbers));
    }

    public enum CaloricLevel{
        DIET,
        NORMAL,
        FAT
    }

    /**
     * @description 一个常见的数据库操作是根据一个或多个属性对集合中的项目进行分组。
     * 就像前面讲到按货币对交易进行分组的例子一样，如果用指令式风格来实现的话，这个操
     * 作可能会很麻烦、啰嗦而且容易出错。
     * 但是，如果用Java 8所推崇的函数式风格来重写的话，就很容易转化为一个非常容易看懂的语句。
     */
    public static void testGroupBy2(){

        //一级分组
       Map<CaloricLevel,List<Dish>> dishesByCaloricLevel=menu.stream()
            .collect(Collectors.groupingBy(
                dish -> {
                   if(dish.getCalories()<=400) {return CaloricLevel.DIET;}
                   if(dish.getCalories()<=700) {return CaloricLevel.NORMAL;}
                   else{ return CaloricLevel.FAT; }
                }
        ));

        System.out.println(gson.toJson(dishesByCaloricLevel));


        //二级分组
       Map<Dish.Type,Map<CaloricLevel,List<Dish>>> groupByTypeAndCaloriesMap=menu.stream()
               .collect(
                Collectors.groupingBy(
                    Dish::getType,
                    Collectors.groupingBy(dish -> {
                        if(dish.getCalories()<=400){
                            return CaloricLevel.DIET;
                        }else if(dish.getCalories()<=700){
                            return CaloricLevel.NORMAL;
                        }else{
                            return CaloricLevel.FAT;
                        }
                })
        ));

        System.out.println(gson.toJson(groupByTypeAndCaloriesMap));

        //分类收集
       Map<Dish.Type,Long> dishTypeCount=menu.stream().collect(Collectors.groupingBy(Dish::getType,Collectors.counting()));

       System.out.println(dishTypeCount);
    }


    /**
     *
     * @description 分区是分组的特殊情况：
     * 由一个谓词（返回一个布尔值的函数）作为分类函数，它称分区函数。
     * 分区函数返回一个布尔值，这意味着得到的分组Map的键类型是Boolean，
     * 于是它最多可以分为两组——true是一组，false是一组。
     *
     */
    public static void testPartitioningBy(){
        Map<Boolean,Map<Boolean,List<Dish>>> result=menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian,
                Collectors.partitioningBy(d->d.getCalories()>500)));
        System.out.println(gson.toJson(result));


       List<Integer> list1= IntStream.range(2,10).boxed().collect(Collectors.toList());
       List<Integer> list2= IntStream.rangeClosed(2,10).boxed().collect(Collectors.toList());
       System.out.println(gson.toJson(list1));
       System.out.println(gson.toJson(list2));


    }

    public static void testMyCollector(){

        List<Dish> dishes = menu.stream().collect(new ToListCollector<Dish>());

        System.out.println(gson.toJson(dishes));

    }

    public static void main(String[] args) {

        TriFunction<Integer, Integer, String, Apple> appleFactory = Apple::new;
        // Apple::new 表示一个生产对象的工厂，这个apply方法，相当于抽象了所有3个参数的构造方法 入参类型为 int,int,string
        Apple apple=appleFactory.apply(1,2,"red");
        System.out.println(gson.toJson(apple));

        //testMathFunction();
        // testDistinct();
        //testFlatStream();
        //testThreeItemArrary();
        //testFiles();
        //testFibo();
        // testGroupBy();
        //testCollectReducing();
        // testGroupBy2();
        // testPartitioningBy();
        testMyCollector();

    }




}
