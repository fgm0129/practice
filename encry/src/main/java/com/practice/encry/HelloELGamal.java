package com.practice.encry;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by fgm on 2017/12/17.
 */
public class HelloELGamal {

    private static String src="被加密的数据,Hello ELGamal";

    public static void main(String[] args) {

        bcELGamal();
    }

    public static void bcELGamal(){


        Security.addProvider(new BouncyCastleProvider());


        try {
            //1.初始化密钥
            AlgorithmParameterGenerator generator=AlgorithmParameterGenerator.getInstance("ElGamal");
            generator.init(512);
            AlgorithmParameters parameters=generator.generateParameters();
            DHParameterSpec dhParameterSpec=parameters.getParameterSpec(DHParameterSpec.class);

            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("ElGamal");
            keyPairGenerator.initialize(dhParameterSpec,new SecureRandom());
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            PublicKey elGamalPublicKey=keyPair.getPublic();
            PrivateKey elGamalPrivateKey=keyPair.getPrivate();

            System.out.println("public key:"+new BASE64Encoder().encode(elGamalPublicKey.getEncoded()));
            System.out.println("private key:"+new BASE64Encoder().encode(elGamalPrivateKey.getEncoded()));

            //2.公钥加密、私钥解密 ------ 加密
            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(elGamalPublicKey.getEncoded());
            KeyFactory  keyFactory=KeyFactory.getInstance("ELGamal");
            PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher= Cipher.getInstance("ELGamal");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] result=cipher.doFinal(src.getBytes());


            System.out.println("公钥加密、私钥解密 ------ 加密"+new BASE64Encoder().encode(result));

            //2.公钥加密、私钥解密 ------ 解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(elGamalPrivateKey.getEncoded());
            keyFactory=KeyFactory.getInstance("ELGamal");
            PrivateKey privateKey= keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher= Cipher.getInstance("ELGamal");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            result=cipher.doFinal(result);
            System.out.println("公钥加密、私钥解密 ------ 解密"+new String(result));




        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
