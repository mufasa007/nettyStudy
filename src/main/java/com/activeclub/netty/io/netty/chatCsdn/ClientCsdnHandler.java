package com.activeclub.netty.io.netty.chatCsdn;

import com.activeclub.netty.io.netty.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author zjk
 * @date 2022/3/22
 * @descript
 * @since
 */
public class ClientCsdnHandler extends SimpleChannelInboundHandler<RpcResponse> {
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
    System.out.println("接收到server响应数据:"+response.toString());
  }
}
