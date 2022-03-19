package com.activeclub.netty.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author 59456
 * @Date 2022/3/19
 * @Descrip
 * @Version 1.0
 */
public class BioTest {
    static final ExecutorService executorService = new ThreadPoolExecutor(
            2,// 核心线程池大小
            5,// 最大核心线程池大小
            3,// 超时了没有人调用就会释放
            TimeUnit.SECONDS,// 超时单位
            new LinkedBlockingQueue<>(1024),// 阻塞队列
            Executors.defaultThreadFactory(),// 线程工厂，创建线程的，一般不会动
            new ThreadPoolExecutor.CallerRunsPolicy());// 拒绝策略

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true){
                System.out.println("等待socket连接");

                // 阻塞方法
                Socket accept = serverSocket.accept();
                System.out.println("检测到客户端连接");

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            hander(accept);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void hander(Socket accept) throws IOException {
        byte[] bytes = new byte[2048];
        System.out.println("准备read。。");

        int read = accept.getInputStream().read(bytes);
        System.out.println("read结束");
        if (read!=-1){
            System.out.println("接收到数据：" + new String(bytes,0,read));
        }
    }
}

/*
windows中的cmd命令下使用telnet直接如下语句
telnet localhost 8000
sen hello
 */