package com.activeclub.netty.io.netty.chatCsdn;

import com.activeclub.netty.io.netty.RpcRequest;
import com.activeclub.netty.io.netty.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

/**
 * @author zjk
 * @date 2022/3/22
 * @descript
 * @since V1.0.0
 */
public class NettyClient {

  private final String host;
  private final int port;
  private Channel channel;

  public NettyClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void start() throws InterruptedException {
    final EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(group)
      .channel(NioSocketChannel.class)
      .handler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
          System.out.println("正在连接中");
          ch.pipeline()
            .addLast(new RpcEncoder(RpcRequest.class))
            .addLast(new RpcDecoder(RpcResponse.class))
            .addLast(new ClientCsdnHandler());
        }
      });

    final ChannelFuture future = bootstrap.connect(host,port).sync();

    future.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if(future.isSuccess()){
          System.out.println("连接服务器成功");
        }else {
          System.out.println("连接服务器失败");
          future.cause().printStackTrace();
          group.shutdownGracefully();
        }
      }
    });

    this.channel = future.channel();
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }
}

// https://blog.csdn.net/qq_22200097/article/details/83042424