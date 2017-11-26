package com.practice.java8;

import com.google.gson.Gson;
import com.practice.java8.model.Dish;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by fgm on 2017/11/23.
 */
public class Charter8 {


    private static final Gson gson=new Gson();

    private static final Logger log=Logger.getLogger(Charter8.class.getName());

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
        rootPath= Charter1.class.getResource("/").getPath();
    }


    public static void doSomething(Task task){
        task.execute();
    }
    public static void doSomething(Runnable runnable){
        runnable.run();
    }

    public static void testLambda(){
        Runnable r1=new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        Runnable r2=()-> System.out.println("Hello");
        r1.run();
        r2.run();


        //将这种匿名类转换为Lambda表达式时，就导致了一种晦涩的方法调用，
        // 因为Runnable和Task都是合法的目标类型
        //doSomething(()-> System.out.println("Hello doSomething"));
        //你可以对Task尝试使用显式的类型转换来解决这种模棱两可的情况：

        doSomething((Task)()-> System.out.println("Hello doSomething"));


    }



    public static void main(String[] args) throws IOException {
        //testLambda();
       // Charter8 charter8=new Charter8();

       // testProcessFile();

        //charter8.testValidationStrategy();
        //testStreamIterator();
        IntStream intStream=tail(numbers());
        int head= intStream.findFirst().getAsInt();
        IntStream filtered=tail(numbers()).filter(n->n%head!=0);

        System.out.println(head);

        System.out.println();


    }

    static IntStream numbers(){
        return IntStream.iterate(2, n -> n + 1);
    }

    static IntStream tail(IntStream numbers){
        return numbers.skip(1);
    }





    public  void testValidationStrategy(){
        Validator numberValidator=new Validator(s->s.matches("\\d+"));
        boolean a1= numberValidator.validator("12345");
        boolean a2= numberValidator.validator("abcd123");
        System.out.println(a1+","+a2);

        Validator lowerCaseValidator=new Validator(s->s.matches("[a-z]+"));
        boolean b1=lowerCaseValidator.validator("abcdefg");
        boolean b2=lowerCaseValidator.validator("abcd123");

        System.out.println(b1+","+b2);


    }


    public  static void testStreamIterator(){

       Stream<Integer> stream=Stream.iterate(0,n->n+2)
               .peek(i-> System.out.println("Hello"+i))
               .limit(10000);

        stream.collect(Collectors.toList());


        IntStream ones = IntStream.generate(() -> 1);



//       List<Integer> list=stream.collect(Collectors.toList());
//        System.out.println(list.size());

    }






    public static void testProcessFile() throws IOException{
        String s1= processFile(rootPath+"data.txt",br->br.readLine());
        String s2= processFile(rootPath+"data.txt",br->br.readLine()+br.readLine());
        System.out.println("s1="+s1);
        System.out.println("s2="+s2);

    }

    public interface ValidationStrategy{
        boolean execute(String  s);
    }
    class Validator{
        private  ValidationStrategy strategy;
        public  Validator(ValidationStrategy strategy){
            this.strategy=strategy;
        }
        public boolean validator(String  s){
            return strategy.execute(s);
        }
    }


    public static String processFile(String filePath,BufferReaderProcessor processor) throws IOException {
        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            return processor.process(br);
        }
    }

    public interface BufferReaderProcessor{
        String process(BufferedReader br) throws IOException;

    }


}
