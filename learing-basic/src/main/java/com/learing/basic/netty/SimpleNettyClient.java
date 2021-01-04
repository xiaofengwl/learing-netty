package com.learing.basic.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/3 8:10 下午
 * @Modified By:
 */
public class SimpleNettyClient {

    /**
     * todo 客户端模拟入口
     * @param args
     */
    public static void main(String[] args) throws Exception{

        while (true){
            //从控制台输入信息
            Scanner content = new Scanner(System.in);
            System.out.print("请输入您要发送的内容: ");
            NettyMessage.CLIENT_MESSAGE = content.nextLine();

            SimpleNettyClient client=new SimpleNettyClient();
            client.connect("127.0.0.1",8088);
        }
    }

    /**
     * todo 客户端发起连接
     * @param host
     * @param port
     */
    private void connect(String host, int port) throws Exception{

        EventLoopGroup worker=new NioEventLoopGroup();
        try{
            // 创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            // 设置线程组
            bootstrap.group(worker);
            // 设置客户端的通道实现类型
            bootstrap.channel(NioSocketChannel.class);

            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            /**
             * 自定义客户端Handle（客户端在这里搞事情）,使用匿名内部类初始化通道
             */
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //添加客户端通道的处理器
                    ch.pipeline().addFirst(new SimpleNettyClientHandler());
                }
            });

            /** 开启客户端监听，连接到远程节点，阻塞等待直到连接完成*/
            /**
             * todo ChannelFuture提供操作完成时一种异步通知的方式。一般在Socket编程中，等待响应结果都是同步阻塞的.
             *      而Netty则不会造成阻塞，因为ChannelFuture是采取类似观察者模式的形式进行获取结果
             */
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            //添加监听器
            channelFuture.addListener(new ChannelFutureListener() {
                //使用匿名内部类，ChannelFutureListener接口
                //重写operationComplete方法
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    //判断是否操作成功
                    if (channelFuture.isSuccess()) {
                        System.out.println("连接成功");
                    } else {
                        System.out.println("连接失败");
                    }
                }
            });
            System.out.println("client start");

            /**阻塞等待数据，直到channel关闭(客户端关闭)*/
            channelFuture.channel().closeFuture().sync();
            System.out.println("client close");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放线程组
            worker.shutdownGracefully();
        }

    }


}
