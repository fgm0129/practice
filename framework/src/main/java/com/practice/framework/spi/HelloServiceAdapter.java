package com.practice.framework.spi;

import java.util.ServiceLoader;

/**
 * Created by fgm on 2017/7/29.
 */
public class HelloServiceAdapter {

    /**
     * @description
     *  ServiceLoader是实现了java.util.Iterator接口的，
     *  而且是基于我们所使用的服务的实现，所以可以通过ServiceLoader的实例来遍历其中的服务实现者，从而调用对应的服务提供者
     */
    public static void main(String[] args) {

        ServiceLoader<HelloService> serviceLoader=ServiceLoader.load(HelloService.class);

        for(HelloService helloService:serviceLoader){
            helloService.sayHello("fangguangming!");
        }


    }

}
