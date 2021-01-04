package com.learing.basic.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
public class SimpleNettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * todo 本方法用于读取客户端发送的信息
     * @param ctx  通道处理器上下文对象:
     *             Netty设计了这个ChannelHandlerContext上下文对象，就可以拿到channel、pipeline等对象，就可以进行读写等操作。
     * @param msg  消息内容
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //todo 处理收到的消息
        System.out.println("收到来自客服端的一条消息");
        ByteBuf result = (ByteBuf) msg;
        byte[] bytesMsg = new byte[result.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(bytesMsg);
        String resultStr = new String(bytesMsg);
        // 接收并打印客户端的信息
        System.out.println("客服端内容:" + resultStr);
        // 释放资源，这行很关键
        result.release();

        //todo 给客户端反馈信息
        //向客户端发送消息
        String response = "我是server，我已经收到你的消息： " + resultStr;
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();

        //通过channel获取ChannelPipeline，并做相关的处理：
        //ChannelPipeline pipeline = ctx.channel().pipeline();
        //往pipeline中添加ChannelHandler处理器，装配流水线
        //pipeline.addLast(new MyServerHandler());
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
