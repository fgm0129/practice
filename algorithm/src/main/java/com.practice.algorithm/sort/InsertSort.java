package com.practice.algorithm.sort;

/**
 * Created by fgm on 2017/7/9.
 * 插入排序、todo 改写成并行模式
 *
 * 数组分成两部分,前半部分是排序的,后半部分是未排序的,
 * 每次都从后半部分选择一个元素插入到前半部分,直到后半部分的数组大小为零
 *
 */
public class InsertSort {
    public static void sort(int[] arr) {
        int j,toInsert;
        for(int i=1;i<arr.length;i++){
             toInsert=arr[i];
             j=i-1;
            while(toInsert<arr[j]&&j>=0){
                arr[j+1]=arr[j];
                j--;
            }
            arr[j+1]=toInsert;
        }


    }


    public static void main(String[] args) {
        int []arr=new int[]{4,56,7,89,78,90,100,18};
        sort(arr);
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }

}
