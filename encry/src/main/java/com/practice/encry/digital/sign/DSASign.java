package com.practice.encry.digital.sign;

import org.bouncycastle.util.encoders.Hex;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by fgm on 2017/12/17.
 */
public class DSASign {


    private static String src="dsa sign security";

    public static void main(String[] args) {
        jdkDSA();

    }

    public static void jdkDSA(){
        try {

            //1.初始化密钥对
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("DSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            DSAPublicKey dsaPublicKey=(DSAPublicKey) keyPair.getPublic();
            DSAPrivateKey dsaPrivateKey=(DSAPrivateKey)keyPair.getPrivate();


            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
            KeyFactory keyFactory=KeyFactory.getInstance("DSA");
            PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature=Signature.getInstance("SHA1withDSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result=signature.sign();
            System.out.println("jdk dsa sign:"+ Hex.toHexString(result));

            //3.验证签名

            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(dsaPublicKey.getEncoded());
            keyFactory=KeyFactory.getInstance("DSA");
            PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
            signature=Signature.getInstance("SHA1withDSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool=signature.verify(result);
            System.out.println("验证结果:"+bool);









        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
