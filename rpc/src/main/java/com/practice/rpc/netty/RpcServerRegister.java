package com.practice.rpc.netty;

import com.practice.rpc.aspect.ServiceProvider;
import com.practice.rpc.test.impl.HelloServiceImpl;
import com.practice.rpc.utils.PackageScanUtil;
import com.practice.rpc.utils.PropertiesUtil;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fgm on 2017/11/30.
 */
public class RpcServerRegister {

    private static final Map<String,Object> services=new ConcurrentHashMap();

    private static final String servicePackage="servicePackage";

    public static boolean  register(Object service){
        String simpleName=service.getClass().getSimpleName();
        services.put(simpleName,service);
        return true;
    }

    public static boolean  register(String name,Object service){
        services.put(name,service);
        return true;
    }

    public static Object getService(String name){
        return services.get(name);
    }

    public static void autoRegister(){
        Properties properties=PropertiesUtil.load("config.properties");
        String path=(String)properties.get(servicePackage);
        List<Class> classList=PackageScanUtil.getClasssFromPackage(path);
        for(Class clazz:classList){
            ServiceProvider provider=  (ServiceProvider)clazz.getAnnotation(ServiceProvider.class);
            try {
                String serviceName=provider.name();
                if(serviceName.equals("")){
                    serviceName=clazz.getSimpleName();
                }

                services.put(serviceName.toLowerCase(),clazz.newInstance());
                System.out.println(serviceName+" has register success!");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    }


    public static void main(String[] args) {
        autoRegister();
        Object o=services.get("helloService");
        System.out.println(o);

    }






}
