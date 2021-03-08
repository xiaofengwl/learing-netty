package com.learing.basic.netty.protocol;

import lombok.Data;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 1:07 下午
 * @Modified By:
 */
@Data
public class NettyMessage {

    private Header header;
    private Object body;

    public String toString() {
        return "NettyMessage [header=" + header + "]";
    }
}
