package com.learing.basic.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/3 8:10 下午
 * @Modified By:
 */
public class SimpleNettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * todo 本方法用于读取客户端发送的信息
     * @param ctx  通道处理器上下文对象
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
