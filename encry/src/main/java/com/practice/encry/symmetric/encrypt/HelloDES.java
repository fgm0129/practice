package com.practice.encry.symmetric.encrypt;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by fgm on 2017/12/17.
 */
public class HelloDES {

    public static void main(String[] args) {

        jdkDES();
    }

    public static void jdkDES() {

        try{
            //生成KEY
            KeyGenerator keyGenerator=KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            SecretKey secetKey=keyGenerator.generateKey();
            byte[] bytesKey=secetKey.getEncoded();

            //KEY装换








        }catch (Exception ex){
            ex.printStackTrace();
        }


    }


}
