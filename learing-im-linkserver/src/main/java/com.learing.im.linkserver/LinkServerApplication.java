package com.learing.im.linkserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/30 10:05 上午
 * @Modified By:
 */
@SpringBootApplication
@EnableScheduling
public class LinkServerApplication {
    /**
     * 启动类
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext
                = SpringApplication.run(LinkServerApplication.class, args);
        /**
         * 获取WebSocketServer实例
         */
//        LinkServerBootstrap clientWebSocketServer
//                = applicationContext.getBean("ClientWebSocketServer", LinkServerBootstrap.class);
        /**
         * 启动webSocketServer实例
         */
        //clientWebSocketServer.start();
    }
}
