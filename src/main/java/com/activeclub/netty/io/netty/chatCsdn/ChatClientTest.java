package com.activeclub.netty.io.netty.chatCsdn;

import com.activeclub.netty.io.netty.RpcRequest;
import io.netty.channel.Channel;

import java.util.UUID;

/**
 * @author zjk
 * @date 2022/3/22
 * @descript
 * @since V1.0.0
 */
public class ChatClientTest {

  public static void main(String[] args) throws InterruptedException {
    NettyClient client = new NettyClient("127.0.0.1",8000);
    client.start();

    Channel channel = client.getChannel();

    RpcRequest request = new RpcRequest();
    request.setId(UUID.randomUUID().toString());
    request.setData("hello world");
    channel.writeAndFlush(request);
  }

}
