package com.practice.rpc.netty;

import com.practice.rpc.netty.handler.DecoderHandler;
import com.practice.rpc.netty.handler.EncoderHandler;
import com.practice.rpc.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by fgm on 2017/11/26.
 */
public class RpcServer {

    private int port;

    public RpcServer(int port){
        this.port=port;
    }


    public void start() throws  Exception{

        ServerBootstrap serverBootstrap=new ServerBootstrap();
        EventLoopGroup boosGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        System.out.println("server listen at port:"+port);
        try{
                   serverBootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline= socketChannel.pipeline();
                           // pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new EncoderHandler());
                            pipeline.addLast(new DecoderHandler());
                            pipeline.addLast(new ServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            Channel ch = serverBootstrap.bind(port).sync().channel();
            ch.closeFuture().sync();

        }finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {

        RpcServerRegister.autoRegister();
        new RpcServer(1234).start();





    }

}
