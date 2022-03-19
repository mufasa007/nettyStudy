package com.activeclub.netty.io.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author 59456
 * @Date 2022/3/19
 * @Descrip
 * @Version 1.0
 */
public class NioSelectorTest {

    public static List<SocketChannel> socketChannelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(8000));
        serverSocket.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT)
        System.out.println("selector-nio启动成功");

        while (true) {
            SocketChannel socketChannel = serverSocket.accept();
            if (socketChannel != null) {
                System.out.println("连接成功");
                socketChannel.configureBlocking(false);
                socketChannelList.add(socketChannel);
            }
            Iterator<SocketChannel> iterator = socketChannelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel next = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                int len = next.read(byteBuffer);
                if (len > 0) {
                    System.out.println("收到消息:" + new String(byteBuffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                    System.out.println("断开连接");
                }

            }
        }


    }
}
