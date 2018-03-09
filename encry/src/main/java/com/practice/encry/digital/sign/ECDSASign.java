package com.practice.encry.digital.sign;


import org.bouncycastle.util.encoders.Hex;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by fgm on 2017/12/17.
 */
public class ECDSASign {

    private static  String src="ecdsa sign";

    public static void main(String[] args) {
        jdkECDSAS();
    }


    public static void jdkECDSAS(){

        try {
            //1.生成密钥对  密钥长度 112~517 默认 256
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(112);
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey=(ECPublicKey)keyPair.getPublic();
            ECPrivateKey ecPrivateKey=(ECPrivateKey)keyPair.getPrivate();

            //2.私钥签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
            KeyFactory keyFactory=KeyFactory.getInstance("EC");
            PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature=Signature.getInstance("SHA1withECDSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result=signature.sign();
            System.out.println("jdk ecdsa sign:"+ Hex.toHexString(result));


            //3.公钥验签
            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(ecPublicKey.getEncoded());
            keyFactory=KeyFactory.getInstance("EC");
            PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);

            signature=Signature.getInstance("SHA1withECDSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool=signature.verify(result);

            System.out.println("jdk ecdsa verify:"+bool);








        } catch (Exception  e) {
            e.printStackTrace();
        }




    }


}
