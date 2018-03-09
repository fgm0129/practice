package com.practice.hack.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * Created by fgm on 2017/12/12.
 */
public class HttpProxy {


    public static void main(String[] args) {


//        HttpProxyServer server =
//                DefaultHttpProxyServer.bootstrap()
//                        .withPort(8080)
//                        .withFiltersSource(new HttpFiltersSourceAdapter() {
//                            @Override
//                            public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
//                                return new HttpFiltersAdapter(originalRequest) {
//                                    @Override
//                                    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
//                                        HttpResponse response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
//
//                                        System.out.println("client to proxy request");
//                                        return response;
//                                    }
//
//                                    @Override
//                                    public HttpObject serverToProxyResponse(HttpObject httpObject) {
//
//                                        System.out.println("server to proxy response");
//                                        return httpObject;
//                                    }
//                                };
//                            }
//                        })
//                        .start();





        System.out.println("proxy server listen on 8080");

    }

}
