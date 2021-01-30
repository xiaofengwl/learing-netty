package com.learing.basic.im.bootstrap;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 服务类的基类
 * @Author lvjun@csdn.net
 * @Date 2021/1/30 9:43 上午
 * @Modified By:
 */
public abstract class LinkServerBootstrap {
    /**
     * 启动服务
     */
    public abstract void start();

    /**
     * 停止服务
     */
    public abstract void stop();

    /**
     * 构建EventLoopGroup
     *
     * @return
     */
    public EventLoopGroup buildEventLoopGroup(){
        // TODO: 2019/5/17  EpollEventLoopGroup NioEventLoopGroup 有何区别
        if (Epoll.isAvailable()) {
            return new EpollEventLoopGroup();
        }else{
            return new NioEventLoopGroup();
        }
    }
}
