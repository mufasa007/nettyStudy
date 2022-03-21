package com.activeclub.netty.io.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {


        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 步骤1：创建服务器端的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 步骤2：使用链式编程来配置参数
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ChatClientHandler())
                                    .addLast("decoder",new StringDecoder())
                                    .addLast("encoder",new StringDecoder());
                        }
                    });

            System.out.println("netty server start ...");

            // 步骤3：绑定一个端口并且同步，生成一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
            // 启动服务器（并绑定端口）bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture cf = bootstrap.bind("127.0.0.1",8000).sync(); // NIO封装
            Channel channel = cf.channel();
            System.out.println("=======" + channel.localAddress() + "=======");
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()){
                String msg = sc.nextLine();
                channel.writeAndFlush(msg);
            }

            // 关闭通道
            cf.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

}
