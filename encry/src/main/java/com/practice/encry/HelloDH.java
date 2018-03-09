package com.practice.encry;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by fgm on 2017/12/17.
 */
public class HelloDH {

    private static  String src="hello dh encrypt";

    private static final int ENCRYPT_MODE=Cipher.ENCRYPT_MODE;

    private static final int DECRYPT_MODE=Cipher.DECRYPT_MODE;



    public static void main(String[] args) {
        jdkDH();
    }


    public static void jdkDH(){


        try {
            //1.初始化发送方密钥
            KeyPair senderKeyPair= generateKeyPair();
            //发送方公钥,发送给接收方(通过网络,文件)
            byte[] senderPublicEnc= senderKeyPair.getPublic().getEncoded();

            //2.初始化接收方密钥
            KeyPair receiverKeyPair=generateKeyPair(senderPublicEnc);

            //3.密钥构建
            PublicKey senderPublicKey=getPublicKeyByEnc(senderPublicEnc);//等价于  PublicKey senderPublicKey=senderKeyPair.getPublic();  但是接收方并不能获取KeyPair
            SecretKey receiverDESKey=generateSecretKey(receiverKeyPair,senderPublicKey);
            byte[] receiverPublicKeyEnc=receiverKeyPair.getPublic().getEncoded();

            PublicKey receiverPublicKey=getPublicKeyByEnc(receiverPublicKeyEnc); // 等价于 PublicKey receiverPublicKey=receiverKeyPair.getPublic();

            SecretKey senderDESKey=generateSecretKey(senderKeyPair,receiverPublicKey);

            if(receiverDESKey==senderDESKey||receiverDESKey.equals(senderDESKey)){
                System.out.println("双方密钥相同");
            }

            //4.加密
            Cipher encryptCipher=getEncryptCipher(senderDESKey);
            byte[] result=encryptCipher.doFinal(src.getBytes());
            System.out.println("jdk dh encrypt:"+Base64.encode(result));


            //5.解密
            Cipher decryptCipher=getDecryptCipher(receiverDESKey);
            result=decryptCipher.doFinal(result);
            System.out.println("jdk dh decrypt:"+new String(result));




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static Cipher getEncryptCipher(SecretKey secretKey) {
        try {
            Cipher cipher=Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            return cipher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Cipher getDecryptCipher(SecretKey secretKey) {
        try {
            Cipher cipher=Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            return cipher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKey generateSecretKey(KeyPair keyPair) {

        return generateSecretKey(keyPair,null);
    }

    private static PublicKey getPublicKeyByEnc(byte[] encodedKey){
        KeyFactory keyFactory= null;
        try {
            keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509=new X509EncodedKeySpec(encodedKey);
            PublicKey publicKey=keyFactory.generatePublic(x509);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private static SecretKey generateSecretKey(KeyPair keyPair,PublicKey publicKey) {
        try {
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(keyPair.getPrivate());
            if(publicKey!=null){
                receiverKeyAgreement.doPhase(publicKey,true);
            }else{
                receiverKeyAgreement.doPhase(keyPair.getPublic(),true);
            }
            SecretKey desKey=receiverKeyAgreement.generateSecret("DES");
            return desKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static KeyPair generateKeyPair(){
           return  generateKeyPair(null);
    }
    private static KeyPair generateKeyPair(byte[] publicKeyEnc){
        try {
            KeyPairGenerator keyPairGenerator= KeyPairGenerator.getInstance("DH");
            if(publicKeyEnc!=null){
                KeyFactory keyFactory=KeyFactory.getInstance("DH");
                X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(publicKeyEnc);
                PublicKey receiverPublicKey=keyFactory.generatePublic(x509EncodedKeySpec);
                DHParameterSpec dhParameterSpec=((DHPublicKey)receiverPublicKey).getParams();
                keyPairGenerator.initialize(dhParameterSpec);
            }else{
                keyPairGenerator.initialize(512);
            }
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
