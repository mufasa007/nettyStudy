package com.activeclub.netty.io.netty.chatCsdn;

import com.activeclub.netty.io.netty.RpcRequest;
import com.activeclub.netty.io.netty.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author zjk
 * @date 2022/3/22
 * @descript
 * @since V1.0.0
 */
public class ServerCsdnHandler extends ChannelInboundHandlerAdapter {

  //接受client发送的消息
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    RpcRequest request = (RpcRequest) msg;
    System.out.println("接收到客户端信息:" + request.toString());
    RpcResponse response = new RpcResponse();
    response.setId(UUID.randomUUID().toString());
    response.setData("server响应结果");
    response.setStatus(1);
    ctx.writeAndFlush(response);
  }

  //通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    System.out.println("服务端接收数据完毕..");
    ctx.flush();
  }

  //读操作时捕获到异常时调用
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    System.out.println("出现异常:");
    cause.printStackTrace();
    ctx.close();
  }


  //客户端去和服务端连接成功时触发
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("客户端连接通道建立完成！");
  }


}
