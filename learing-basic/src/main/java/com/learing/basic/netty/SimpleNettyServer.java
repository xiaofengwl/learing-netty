package com.learing.basic.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
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
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);  //线程数1          接收请求交给worker
        //todo 从线程组
        EventLoopGroup workGroup=new NioEventLoopGroup(16); //线程数16         处理事件

        try{
            //netty服务器的辅助启动类，netty服务开始的地方
            ServerBootstrap serverBootstrap=new ServerBootstrap();

            serverBootstrap.group(bossGroup,workGroup)              //todo 绑定2个线程组
                            //todo 设置服务端通道实现类型
                            .channel(NioServerSocketChannel.class)
                            //todo option()设置的是服务端用于接收进来的连接，也就是boosGroup线程。
                            // SO_BACKLOG Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128。
                            .option(ChannelOption.SO_BACKLOG, 128)
                            //todo childOption()是提供给父管道接收到的连接，也就是workerGroup线程。
                            //SO_RCVBUF Socket参数，TCP数据接收缓冲区大小。
                            //TCP_NODELAY TCP参数，立即发送数据，默认值为Ture。
                            //SO_KEEPALIVE Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。
                            .childOption(ChannelOption.SO_KEEPALIVE, true)
                            //todo 使用匿名内部类的形式初始化通道对象
                            .childHandler(new ChannelInitializer<SocketChannel>() {//todo 子处理器，用于处理workerGroup
                                                @Override
                                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                                    //todo 给pipeline管道设置处理器
                                                    /**
                                                     * 处理器Handler主要分为两种：
                                                     * ChannelInboundHandlerAdapter(入站处理器)、ChannelOutboundHandler(出站处理器)
                                                     */
                                                    socketChannel.pipeline().addLast(new SimpleNettyServerHandler());
                                                }
                            });
            //启动服务，绑定端口
            /**
             * todo ChannelFuture提供操作完成时一种异步通知的方式。一般在Socket编程中，等待响应结果都是同步阻塞的.
             *      而Netty则不会造成阻塞，因为ChannelFuture是采取类似观察者模式的形式进行获取结果
             */
            ChannelFuture channelFuture=serverBootstrap.bind(port).sync();
            //添加监听器，自动返回结果通知线程
            channelFuture.addListener(new ChannelFutureListener() {
                //使用匿名内部类，ChannelFutureListener接口
                //重写operationComplete方法
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    //判断是否操作成功
                    if (channelFuture.isSuccess()) {
                        System.out.println("连接成功");
                    } else if(channelFuture.cause()!=null) {
                        System.out.println("执行失败");
                    }else if(channelFuture.isCancelled()){
                        System.out.println("撤销执行");
                    }

                    //释放资源，退出
                    System.out.println(channelFuture.channel().toString()+" 链路关闭");
                    bossGroup.shutdownGracefully();
                    workGroup.shutdownGracefully();

                }
            });
            System.out.println("server start");

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();   //主线程一直
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
