package com.learing.basic.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 协议编解码~1,根据自定义的协议编码
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:11 下午
 * @Modified By:
 */
public class CustomEncoder extends MessageToByteEncoder<CustomProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        System.out.println("CustomEncoder encode invoked");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }

}
