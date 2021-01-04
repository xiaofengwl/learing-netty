package com.learing.basic.nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/4 10:14 下午
 * @Modified By:
 */
public class NIOClient {

    /**
     * todo NIO客户端
     * @param args
     */
    public static void main(String[] args) throws Exception {

        /**
         * todo 客户端连接
         */
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //连接服务器
        boolean connect = socketChannel.connect(address);

        //判断是否连接成功
        if(!connect){
            //等待连接的过程中
            while (!socketChannel.finishConnect()){
                System.out.println("连接服务器需要时间，期间可以做其他事情...");
            }
        }else{
            System.out.println("连接服务器，，，，successfully");
        }

        String msg = "hello java技术爱好者！";
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        //把byteBuffer数据写入到通道中
        socketChannel.write(byteBuffer);
        //让程序卡在这个位置，不关闭连接
        System.in.read();
    }

    /**
     * SelectionKey
     *  todo 在SelectionKey类中有四个常量表示四种事件，来看源码：
     *  public abstract class SelectionKey {
     *     //读事件
     *     public static final int OP_READ = 1 << 0; //2^0=1
     *     //写事件
     *     public static final int OP_WRITE = 1 << 2; // 2^2=4
     *     //连接操作,Client端支持的一种操作
     *     public static final int OP_CONNECT = 1 << 3; // 2^3=8
     *     //连接可接受操作,仅ServerSocketChannel支持
     *     public static final int OP_ACCEPT = 1 << 4; // 2^4=16
     * }
     *
     *
     *
     */

}
