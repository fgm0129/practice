package com.practice.rpc.netty;

import com.google.gson.Gson;
import com.practice.api.CalculatorService;
import com.practice.api.HelloService;
import com.practice.api.dto.GreetDto;
import com.practice.rpc.core.BizException;
import com.practice.rpc.core.RequestBean;
import com.practice.rpc.core.ResponseBean;
import com.practice.rpc.netty.handler.ClientHandler;
import com.practice.rpc.netty.handler.DecoderHandler;
import com.practice.rpc.netty.handler.EncoderHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by fgm on 2017/11/30.
 */
public class RpcClient {

    private static final String host="127.0.0.1";
    private static final int port=1234;

    private static final Gson gson=new Gson();

    private static ClientHandler clientHandler=new ClientHandler();

    public static final Channel getChannel(String host, int port, EventLoopGroup eventLoopGroup){
        Channel channel=null;
        try {
            channel=getBootstrap(eventLoopGroup).connect(host,port).sync().channel();
        } catch (Exception e) {
            return null;
        }
        return channel;
    }


    public static final Bootstrap getBootstrap(EventLoopGroup eventLoopGroup){
        Bootstrap bootstrap=null;
        try{
            bootstrap=new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline=channel.pipeline();
                           // pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new EncoderHandler());
                            pipeline.addLast(new DecoderHandler());
                            pipeline.addLast(clientHandler);

                        }
                    });
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return bootstrap;
    }

    public static final void request(RequestBean requestBean){
        ChannelFuture channelFuture=null;
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try{
            channelFuture=getChannel(host,port,eventLoopGroup).writeAndFlush(requestBean);

            channelFuture.sync();
            channelFuture.channel().closeFuture().sync();

        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }


    public static <T>T refer(final Class<T> interfaceClass,final String serviceName,final String host, final int port){
        if(serviceName==null||serviceName.equals("")){
            return refer(interfaceClass,host,port);
        }
        ClassLoader loader=interfaceClass.getClassLoader();
        Class<?>[] interfaces=new Class[]{interfaceClass};
        InvocationHandler h= (proxy, method, args) -> {
            RequestBean requestBean=new RequestBean();
            requestBean.setServiceName(serviceName);
            requestBean.setMethodName(method.getName());
            requestBean.setParameterTypes(method.getParameterTypes());
            requestBean.setArguments(args);
            requestBean.setRequestTime(System.currentTimeMillis());
            request(requestBean);
            ResponseBean responseBean=clientHandler.getResponseBean();
            if(null!=responseBean&&ResponseBean.SUCCESS.equals(responseBean.getRetCode())){
                return responseBean.getResult();
            }else{
                throw new BizException(responseBean.getRetCode(),responseBean.getRetMessage());
            }
        };
        return  (T)Proxy.newProxyInstance(loader,interfaces,h);

    }


    public static <T>T refer(final Class<T> interfaceClass,final String host, final int port){
        ClassLoader loader=interfaceClass.getClassLoader();
        Class<?>[] interfaces=new Class[]{interfaceClass};
        InvocationHandler h= (proxy, method, args) -> {
            String simpleName=interfaceClass.getSimpleName();
            RequestBean requestBean=new RequestBean();
            requestBean.setServiceName(simpleName);
            requestBean.setMethodName(method.getName());
            requestBean.setParameterTypes(method.getParameterTypes());
            requestBean.setArguments(args);
            requestBean.setRequestTime(System.currentTimeMillis());
            request(requestBean);
            ResponseBean responseBean=clientHandler.getResponseBean();
            if(null!=responseBean&&ResponseBean.SUCCESS.equals(responseBean.getRetCode())){
                return responseBean.getResult();
            }else{
                throw new BizException(responseBean.getRetCode(),responseBean.getRetMessage());
            }
        };
       return  (T)Proxy.newProxyInstance(loader,interfaces,h);


    }




    public static void main(String[] args) throws InterruptedException {

        HelloService helloService=refer(HelloService.class,host,port);

        CalculatorService  calculator=refer(CalculatorService.class,"calculatorServiceImpl",host,port);



//        String result=helloService.hello("rpc!");
//        GreetDto greetDto= helloService.greet("fangguangming");
//        System.out.println(result);
//
//        System.out.println(gson.toJson(greetDto));
//
//
//        double sum=calculator.add(1.2,1.3);
//
//        double a3=calculator.divide(3,2);

       calculator.divide(3,0);

//        System.out.println("sum 1.2+1.3="+sum);
//        System.out.println("divide 3/2="+a3);
//        System.out.println("divide 3/0="+a4);










    }
}
