package com.learing.basic.nio;

/**
 * TODO Selector 选择器，多路复用器
 * @Author lvjun@csdn.net
 * @Date 2021/1/4 9:09 下午
 * @Modified By:
 */
public class SelectorLearn {

    /**
     * todo
     * @param args
     */
    public static void main(String[] args) {
        
    }

    /**
     * todo 描述
     */
    static void description(){
        /**
         * 其实NIO的主要用途是网络IO，在NIO之前java要使用网络编程就只有用Socket。
         * 而Socket是阻塞的，显然对于高并发的场景是不适用的。所以NIO的出现就是解决了这个痛点。
         *
         * 主要思想是把Channel通道注册到Selector中，通过Selector去监听Channel中的事件状态，
         * 这样就不需要阻塞等待客户端的连接，从主动等待客户端的连接，变成了通过事件驱动。没有监听的事件，服务器可以做自己的事情。
         */
    }



}
