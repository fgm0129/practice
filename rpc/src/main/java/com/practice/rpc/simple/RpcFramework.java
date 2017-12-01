package com.practice.rpc.simple;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fgm on 2017/7/23.
 *
 * 简易版RPC框架
 * 利用Socket和ServerSocket通讯
 *
 */
public class RpcFramework {

    private static  boolean toStop=false;
    private static String stopReason=null;

    public void stop(String reason){
        toStop=true;
        stopReason=reason;
    }


    public static void export(final Object service,int port) throws Exception{
        if(service==null){
            throw new IllegalArgumentException("export service cannot not be null!");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }

        final ServerSocket serverSocket=new ServerSocket(port);
        while(!toStop){
            try{
                System.out.println("server is running at "+port);
               final  Socket socket=serverSocket.accept();
                Thread thread=new Thread(new Runnable() {
                 public void run() {
                        ObjectInputStream inputStream=null;
                        ObjectOutputStream outputStream=null;
                         try{
                             inputStream=new ObjectInputStream(socket.getInputStream());
                             String methodName =inputStream.readUTF();
                             Class<?>[] parameterTypes = (Class<?>[])inputStream.readObject();
                             Object[] arguments =(Object[])inputStream.readObject();

                             outputStream=new ObjectOutputStream(socket.getOutputStream());

                             Method method=service.getClass().getMethod(methodName,parameterTypes);
                             Object result=method.invoke(service,arguments);

                             outputStream.writeObject(result);

                         }catch (Exception ex){
                             ex.printStackTrace();
                         }finally{
                             outputStreamClose(outputStream);
                             inputStreamClose(inputStream);
                             socketClose(socket);
                         }

                     }


                });
              thread.start();



            }catch (Exception ex){
                ex.printStackTrace();
            }


        }

        System.out.println("server is stoped,reason is "+stopReason);


    }


    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception{
        if(interfaceClass==null){
            throw new IllegalArgumentException("interfaceClass cannot be null!");
        }
        if (! interfaceClass.isInterface()) {
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Host == null!");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }


        return  (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
           public Object  invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ObjectOutputStream out = null;
                ObjectInputStream input = null;
                Socket socket = null;
                Object result=null;
                try {
                    socket = new Socket(host, port);
                    out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeUTF(method.getName());
                    out.writeObject(method.getParameterTypes());
                    out.writeObject(args);
                    input = new ObjectInputStream(socket.getInputStream());
                    result = input.readObject();
                    if (result instanceof Throwable) {
                        throw (Throwable) result;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    inputStreamClose(input);
                    outputStreamClose(out);
                    socket.close();
                }
                return result;
           }
       });



    }

    public static <T> T refer2(final Class<T> interfaceClass, final String host, final int port) throws Exception{
        if(interfaceClass==null){
            throw new IllegalArgumentException("interfaceClass cannot be null!");
        }
        if (! interfaceClass.isInterface()) {
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Host == null!");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }


        return null;

    }




    private  static void socketClose(Socket socket){
        if(socket==null){
            return;
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void outputStreamClose(ObjectOutputStream output) {
        if(output==null){
            return;
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void inputStreamClose(ObjectInputStream input) {
        if(input==null){
            return;
        }
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
