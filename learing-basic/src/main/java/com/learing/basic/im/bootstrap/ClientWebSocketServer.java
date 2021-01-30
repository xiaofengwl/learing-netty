package com.learing.basic.im.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/30 9:45 上午
 * @Modified By:
 */
public class ClientWebSocketServer extends LinkServerBootstrap {

    /**
     *
     */
    private int port;
    private String deployModeConfig;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private Channel ch;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
