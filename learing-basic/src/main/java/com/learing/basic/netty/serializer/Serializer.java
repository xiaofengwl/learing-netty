package com.learing.basic.netty.serializer;

import java.io.IOException;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:43 下午
 * @Modified By:
 */
public interface Serializer {
    /**
     * java对象转换为二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * 二进制转换成java对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException;
}
