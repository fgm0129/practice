package com.practice.rpc.netty.handler;

import com.google.gson.Gson;
import com.practice.rpc.core.ResponseBean;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * Created by fgm on 2017/11/30.
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ResponseBean> {

    private static final Gson gson=new Gson();

    private ResponseBean responseBean;

    public ResponseBean getResponseBean() {
        return responseBean;
    }


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ResponseBean responseBean) throws Exception {
        this.responseBean=responseBean;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

}
