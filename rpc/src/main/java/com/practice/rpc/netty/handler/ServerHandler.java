package com.practice.rpc.netty.handler;

import com.practice.rpc.core.BizException;
import com.practice.rpc.core.RequestBean;
import com.practice.rpc.core.ResponseBean;
import com.practice.rpc.netty.RpcServerRegister;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;


/**
 * Created by fgm on 2017/11/30.
 */
public class ServerHandler  extends SimpleChannelInboundHandler<RequestBean> {


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, RequestBean requestBean) throws Exception {

        String serviceName=requestBean.getServiceName().toLowerCase();
        Object service= RpcServerRegister.getService(serviceName);
        Method method=service.getClass().getMethod(requestBean.getMethodName(),requestBean.getParameterTypes());
        ResponseBean responseBean=new ResponseBean();
        try{
            Object result=method.invoke(service,requestBean.getArguments());
            responseBean.setResult(result);
            responseBean.setRetCode(ResponseBean.SUCCESS);
            responseBean.setRetMessage("success");
        }catch (Exception ex){
            responseBean.setRetCode(ResponseBean.ERROR);
            Throwable cause = ex.getCause();
            if(cause instanceof BizException){
                responseBean.setRetMessage(cause.getMessage());
            }else{
                responseBean.setRetMessage(ex.getMessage());
            }
        }
        responseBean.setResponseTime(System.currentTimeMillis());

        channelHandlerContext.channel().writeAndFlush(responseBean);


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       // ctx.channel().disconnect().sync();

    }
}
