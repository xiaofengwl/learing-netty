package com.learing.basic.netty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义协议
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:07 下午
 * @Modified By:
 */
@Data
@AllArgsConstructor
public class CustomProtocol {

    private int length;
    private byte[] content;

}
