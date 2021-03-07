package com.learing.basic.netty.rpc;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:52 下午
 * @Modified By:
 */
@Data
public class RpcRequest implements Serializable {

    /**
     * 请求对象的ID
     */
    private String requestId;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 入参
     */
    private Object[] parameters;

}