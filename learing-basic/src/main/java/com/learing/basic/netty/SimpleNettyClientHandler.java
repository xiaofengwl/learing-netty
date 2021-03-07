package com.learing.basic.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/3 8:10 下午
 * @Modified By:
 */
public class SimpleNettyClientHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    private int count;
    /**
     * todo 服务端发过来消息时调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("客服端收到来自服务端的消息: ");
        System.out.println("消息长度： " + length);
        System.out.println("消息内容： " + new String(content, StandardCharsets.UTF_8));
        System.out.println("客服端收到来自服务端的消息数: " + (++count));
    }

    /**
     * todo 连接到服务器调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = NettyMessage.CLIENT_MESSAGE;
        int length = message.getBytes(StandardCharsets.UTF_8).length;
        byte[] content = message.getBytes(StandardCharsets.UTF_8);
        //封装协议
        CustomProtocol protocol = new CustomProtocol(length, content);
        ctx.channel().writeAndFlush(protocol);
    }

    /**
     * todo 异常时调用
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
