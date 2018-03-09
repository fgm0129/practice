package com.practice.encry.digital.sign;

import org.bouncycastle.util.encoders.Hex;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by fgm on 2017/12/17.
 */
public class RSASign {

    private static String src="rsa security";

    public static void main(String[] args) {
       // jdkRSA();
        jdkKeyCheck();
    }


    /**
     * @description 私钥签名,公钥验证
     *
     */
    public static void jdkRSA(){

        try {
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair= keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey=(RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey=(RSAPrivateKey)keyPair.getPrivate();

            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature=Signature.getInstance("MD5WithRSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result=signature.sign();
            System.out.println("jdk rsa sign:"+ Hex.toHexString(result));

            //3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory=KeyFactory.getInstance("RSA");
            PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
            signature=Signature.getInstance("MD5WithRSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool=signature.verify(result);
            System.out.println("jdk rsa verify:"+bool);






        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void jdkKeyCheck(){

        try {
            KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair keyPair=kpg.generateKeyPair();
            RSAPrivateKey rsaPrivateKey=(RSAPrivateKey) keyPair.getPrivate();

            byte[] keyBytes=keyPair.getPrivate().getEncoded();
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey=(RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);






        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
