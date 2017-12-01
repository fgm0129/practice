package com.practice.rpc.netty;

import com.practice.api.HelloService;
import com.practice.rpc.core.RequestBean;

import java.io.*;
import java.lang.reflect.Method;

/**
 * Created by fgm on 2017/11/30.
 */
public class ByteObjConverter {

    public static Object byteToObject(byte[] bytes){
        ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
        ObjectInputStream oi=null;
        try {
            oi=new ObjectInputStream(inputStream);
            return oi.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally{

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        return null;
    }

    public static byte[] objectToByte(Object object){
        ByteArrayOutputStream outputStream=null;
        ObjectOutputStream oos=null;
        try {
            outputStream=new ByteArrayOutputStream();
             oos=new ObjectOutputStream(outputStream);
            oos.writeObject(object);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                outputStream.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) {

        Class clazz=HelloService.class;
        Class<?>[]  parameterTypes=null;
        Object []arguments=new String[]{"hello fangguangming"};
        for(Method method:clazz.getMethods()){
           if(method.getName().equals("hello")){
               parameterTypes=method.getParameterTypes();
           }

        }


        RequestBean requestBean=new RequestBean();
        requestBean.setServiceName("HelloService");
//        requestBean.setParameterTypes(parameterTypes);
//        requestBean.setArguments(arguments);
        byte[] objectByte=objectToByte(requestBean);



        RequestBean bean=(RequestBean)byteToObject(objectByte);




       // System.out.println(bean.getServiceName()+","+bean.getArguments()+","+bean.getParameterTypes());

    }



}
