package com.activeclub.netty.io.netty.chatCsdn;

import com.activeclub.netty.io.netty.RpcRequest;
import com.activeclub.netty.io.netty.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zjk
 * @date 2022/3/22
 * @descript
 * @since V1.0.0
 */
public class NettyServer {

  public void bind(int port) throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(workerGroup, workerGroup)
      .channel(NioServerSocketChannel.class)
      .option(ChannelOption.SO_BACKLOG,128)
      .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
      .childHandler(new ChannelInitializer<SocketChannel>() {

        @Override
        protected void initChannel(SocketChannel sh) throws Exception {
          sh.pipeline()
            .addLast(new ServerCsdnHandler())
            .addLast(new RpcDecoder(RpcRequest.class))
            .addLast(new RpcEncoder(RpcResponse.class));
        }
      });

    final ChannelFuture future = serverBootstrap.bind(port).sync();

    if(future.isSuccess()){
      System.out.println("服务端启动成功! ");
    }else {
      System.out.println("服务端启动失败! ");
      future.cause().printStackTrace();
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }

    future.channel().closeFuture().sync();

  }

}
