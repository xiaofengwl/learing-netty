package com.learing.basic.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/3 7:57 下午
 * @Modified By:
 */
public class SimpleNettyServer {

    /**
     * 服务端启动入口
     * @param args
     */
    public static void main(String[] args) throws Exception {
        int port=8088;
        new SimpleNettyServer().bind(port);
    }

    /**
     *
     * @param port
     * @throws Exception
     */
    public void bind(int port)throws Exception{
        //todo 主线程组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        //todo 从线程组
        EventLoopGroup workGroup=new NioEventLoopGroup();

        try{
            //netty服务器启类
            ServerBootstrap serverBootstrap=new ServerBootstrap();

            serverBootstrap.group(bossGroup,workGroup)              //todo 绑定2个线程组
                           //todo 用于构建socketChannel工厂
                           .channel(NioServerSocketChannel.class)   //todo 指定NIO的模式
                           .childHandler(new ChannelInitializer<SocketChannel>() {//todo 子处理器，用于处理workerGroup
                               @Override
                               protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    //todo 注册处理器
                                    socketChannel.pipeline().addLast(new SimpleNettyServerHandler());
                               }
                           });
            //启动服务，绑定端口
            ChannelFuture channelFuture=serverBootstrap.bind(port).sync();
            System.out.println("server start");

            //监听关闭的channel,等到服务器的socket关闭，设置同步方式
            channelFuture.channel().closeFuture().sync();
            System.out.println("server close");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //退出线程组
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
