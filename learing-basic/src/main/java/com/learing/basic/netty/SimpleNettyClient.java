package com.learing.basic.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
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
            // 客户端启动类程序
            Bootstrap bootstrap = new Bootstrap();
            //EventLoop的组
            bootstrap.group(worker);
            //用于构造socketchannel工厂
            bootstrap.channel(NioSocketChannel.class);

            /**
             * 设置选项
             * 参数：Socket的标准参数（key，value），可自行百度保持呼吸，不要断气！
             */
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            /**
             * 自定义客户端Handle（客户端在这里搞事情）
             */
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addFirst(new SimpleNettyClientHandler());
                }
            });

            /** 开启客户端监听，连接到远程节点，阻塞等待直到连接完成*/
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
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
