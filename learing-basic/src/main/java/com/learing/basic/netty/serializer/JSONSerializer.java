package com.learing.basic.netty.serializer;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:43 下午
 * @Modified By:
 */
public class JSONSerializer implements Serializer {
    public byte[] serialize(Object object) throws IOException {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
        return JSON.parseObject(bytes, clazz);
    }
}
