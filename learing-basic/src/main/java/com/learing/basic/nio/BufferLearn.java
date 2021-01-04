package com.learing.basic.nio;

import java.nio.ByteBuffer;

/**
 * TODO 缓冲区学习
 * @Author lvjun@csdn.net
 * @Date 2021/1/4 7:36 下午
 * @Modified By:
 */
public class BufferLearn {

    /**
     * todo
     * @param args
     */
    public static void main(String[] args) {
        byteBuffer();
    }

    /**
     * todo 功能介绍
     */
    static void description(){
        //todo 1, ByteBuffer
        //创建堆内内存块HeapByteBuffer
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);

        String msg = "java技术爱好者";
        //包装一个byte[]数组获得一个Buffer，实际类型是HeapByteBuffer
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(msg.getBytes());

        //创建堆外内存块(直接缓冲区)的方法：
        ByteBuffer byteBuffer3 = ByteBuffer.allocateDirect(1024);

        /**
         * 其实根据类名就可以看出，HeapByteBuffer所创建的字节缓冲区就是在JVM堆中的，即JVM内部所维护的字节数组。
         * 而DirectByteBuffer是直接操作操作系统本地代码创建的内存缓冲数组。
         *
         * (1) DirectByteBuffer的使用场景：
         *     1, java程序与本地磁盘、socket传输数据
         *     2, 大文件对象，可以使用。不会受到堆内存大小的限制。
         *     3, 不需要频繁创建，生命周期较长的情况，能重复使用的情况。
         * (2) HeapByteBuffer的使用场景：
         *     除了以上的场景外，其他情况还是建议使用HeapByteBuffer，没有达到一定的量级，
         *     实际上使用DirectByteBuffer是体现不出优势的。
         */
    }

    /**
     * todo 简单的ByteBuffer demo
     * 说明：
     * 抽象基类Buffer有以下三个重要参数：
     *      //位置，默认是从第一个开始
     *      private int position = 0;
     *      //限制，不能读取或者写入的位置索引
     *      private int limit;
     *      //容量，缓冲区所包含的元素的数量
     *      private int capacity;
     */
    static void byteBuffer(){
        String msg="nio从入门到踹门";
        //创建一个固定大小的buffer(返回的是HeapByteBuffer)
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        byte[] bytes=msg.getBytes();
        //写入数据到Buffer中
        byteBuffer.put(bytes);

        //切换成读模式，关键:这一步不可以少，靠此操作来定位出，数据长度和位置索引，方便后面get的时候读取
        //todo flip()方法是很重要的。意思是切换到读模式
        /**
         * flip()方法实现如下：
         * public final Buffer flip() {
         *         limit = position;
         *         position = 0;
         *         mark = -1;
         *         return this;
         * }
         * 为什么要这样赋值呢？因为下面有一句循环条件判断：
         * public final boolean hasRemaining() {
         *      //判断position的索引是否小于limit。
         *      //所以可以看出limit的作用就是记录写入数据的位置，那么当读取数据时，就知道读到哪个位置
         *      return position < limit;
         * }
         * 所以可以看出实质上capacity容量大小是不变的，实际上是通过控制position和limit的值来控制读写的数据。
         */
        byteBuffer.flip();

        //创建一个临时数组，用于存储获取到的数据
        byte[] tempByte = new byte[bytes.length];
        int i = 0;
        //如果还有数据，就循环。循环判断条件
        while (byteBuffer.hasRemaining()) {
            //获取byteBuffer中的数据
            byte b = byteBuffer.get();
            //放到临时数组中
            tempByte[i] = b;
            i++;
            System.out.println("i="+new String(tempByte));
        }
        System.out.println(new String(tempByte));
    }


}
