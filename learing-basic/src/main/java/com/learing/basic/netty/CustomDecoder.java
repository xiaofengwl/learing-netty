package com.learing.basic.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 协议编解码~2,根据自定义的协议解码
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:09 下午
 * @Modified By:
 */
public class CustomDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("CustomDecoder decode invoked ");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);
        //封装协议
        CustomProtocol protocol = new CustomProtocol(length, content);
        out.add(protocol);
    }

}
