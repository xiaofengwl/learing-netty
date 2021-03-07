package com.learing.basic.netty.rpc;

import com.learing.basic.netty.serializer.JSONSerializer;
import com.learing.basic.netty.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * rpc解码器
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:50 下午
 * @Modified By:
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    private Serializer serializer;

    public RpcDecoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    /**
     * 解码
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.isReadable()) {
            int dataLength = in.readableBytes();
            byte[] data = new byte[dataLength];
            in.readBytes(data);
            String s = new String(data);
            String substring = s.substring(4);
            RpcRequest rpcRequest = new JSONSerializer().deserialize(RpcRequest.class, substring.getBytes());
            System.out.println(rpcRequest);
            out.add(rpcRequest); // 将数据添加进去
        }
    }
}