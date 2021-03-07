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
        /**
         * 主线程组:bossGroup
         * 从线程组:workGroup
         *
         * 如果不设置线程数，则默认：CPU核数*2倍
         *
         */
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);  //线程数1          接收请求交给worker
        EventLoopGroup workGroup=new NioEventLoopGroup(16); //线程数16         处理事件

        try{
            //netty服务器的辅助启动类，netty服务开始的地方
            ServerBootstrap serverBootstrap=new ServerBootstrap();

            serverBootstrap.group(bossGroup,workGroup)              //todo 绑定2个线程组
                            /**
                             * 设置服务端通道实现类型
                             */
                            .channel(NioServerSocketChannel.class)
                            /**
                             * boosGroup主线程
                             * (1)option()设置的是服务端用于接收进来的连接，也就是boosGroup线程。
                             *    SO_BACKLOG Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128。
                             * (2)handler()，主要用于装配parent通道，也就是bossGroup线程
                             */
                            .option(ChannelOption.SO_BACKLOG, 128)
                            /*.handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    System.out.println("********handler()*********");
                                }
                            })*/
                            /**
                             * workerGroup工作线程
                             * (1)childOption()是提供给父管道接收到的连接，也就是workerGroup线程。
                             *    SO_RCVBUF Socket参数，TCP数据接收缓冲区大小。
                             *    TCP_NODELAY TCP参数，立即发送数据，默认值为Ture。
                             *    SO_KEEPALIVE Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。
                             * (2)childHandler(),使用匿名内部类的形式初始化通道对象
                             *
                             */
                            .childOption(ChannelOption.SO_KEEPALIVE, true)
                            .childHandler(new ChannelInitializer<SocketChannel>() {

                                @Override
                                protected void initChannel(SocketChannel sch) throws Exception {
                                    System.out.println("********childHandler()*********");
                                    /**
                                     * 给pipeline管道设置处理器,处理器Handler主要分为两种：
                                     * 1,ChannelInboundHandlerAdapter(入站处理器)
                                     *   入站指的是数据从底层java NIO Channel到Netty的Channel。
                                     * 2,ChannelOutboundHandler(出站处理器)
                                     *   出站指的是通过Netty的Channel来操作底层的java NIO Channel。
                                     */
                                    ChannelPipeline pipeline= sch.pipeline();
                                    pipeline.addLast(new CustomDecoder());              //加入解码器
                                    pipeline.addLast(new CustomEncoder());              //加入编码器
                                    pipeline.addLast(new SimpleNettyServerHandler());   //加入处理器
                                }

                            });
            /**
             * 启动服务，绑定端口
             * ChannelFuture提供操作完成时一种异步通知的方式。
             * 一般在Socket编程中，等待响应结果都是同步阻塞的，而Netty则不会造成阻塞，因为ChannelFuture是采取类似观察者模式的形式进行获取结果
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
                }
            });
            System.out.println("server start");

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();   //主线程一直
            System.out.println("server close");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //优雅地关闭EventLoopGroup,释放资源，退出
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
