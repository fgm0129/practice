package com.practice.algorithm.sort;

/**
 * Created by fgm on 2017/7/9.
 * 冒泡排序、TODO 改写成并行模式
 */
public class BubbleSort {
    public static void sort(int[] arr) {
        for(int i=arr.length-1;i>0;i--){
            for(int j=0;j<i;j++){
                if(arr[j]>arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }

            }
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
