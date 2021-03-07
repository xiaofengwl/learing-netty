package com.learing.basic.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * TODO 自定义的Handler需要继承Netty规定好的HandlerAdapter,才能被Netty框架所关联，有点类似SpringMVC的适配器模式
 * @Author lvjun@csdn.net
 * @Date 2021/1/3 8:10 下午
 * @Modified By:
 * todo 处理器Handler主要分为两种：
 *     ChannelInboundHandlerAdapter(入站处理器):
 *          入站指的是数据从底层java NIO Channel到Netty的Channel。
 *     ChannelOutboundHandler(出站处理器):
 *          出站指的是通过Netty的Channel来操作底层的java NIO Channel。
 *
 * todo  * Netty设计了这个ChannelHandlerContext上下文对象，就可以拿到channel、pipeline等对象，就可以进行读写等操作。
 */
public class SimpleNettyServerHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    /**
     * (1)，ChannelInboundHandlerAdapter处理器常用的事件有：
     *  注册事件 fireChannelRegistered。
     *  连接建立事件 fireChannelActive。
     *  读事件和读完成事件 fireChannelRead、fireChannelReadComplete。
     *  异常通知事件 fireExceptionCaught。
     *  用户自定义事件 fireUserEventTriggered。
     *  Channel 可写状态变化事件 fireChannelWritabilityChanged。
     *  连接关闭事件 fireChannelInactive。
     *
     *
     * (2),ChannelOutboundHandler处理器常用的事件有：
     *  端口绑定 bind。
     *  连接服务端 connect。
     *  写事件 write。
     *  刷新时间 flush。
     *  读事件 read。
     *  主动断开连接 disconnect。
     *  关闭 channel 事件 close。
     *
     */

    private int count;

    /**
     * todo 本方法用于读取客户端发送的信息
     * @param ctx  通道处理器上下文对象:
     *             Netty设计了这个ChannelHandlerContext上下文对象，就可以拿到channel、pipeline等对象，就可以进行读写等操作。
     * @param msg  消息内容
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {

        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端收到的内容： ");
        System.out.println("内容长度：  " + length);
        System.out.println("内容： " + new String(content, StandardCharsets.UTF_8));
        System.out.println("服务端收到消息的数量： " + (++count));
        //向客户端返回数据
        String responseMsg = LocalDateTime.now().toString();
        int responseLength = responseMsg.getBytes(StandardCharsets.UTF_8).length;
        byte[] responseContent = responseMsg.getBytes(StandardCharsets.UTF_8);
        //封装协议
        CustomProtocol protocol = new CustomProtocol(responseLength, responseContent);
        ctx.channel().writeAndFlush(protocol);
    }

    /**
     * todo 信息获取完毕后操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * todo 本方法用作处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
