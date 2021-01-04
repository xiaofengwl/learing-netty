package com.learing.basic.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * TODO 管道学习Channel
 * @Author lvjun@csdn.net
 * @Date 2021/1/4 8:11 下午
 * @Modified By:
 *
 */
public class ChannelLearn {

    /**
     * todo
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //fileChannel();
        //socketChannel();
        fileChannelGatheringOrScattering();
    }

    /**
     * todo 描述
     */
    static void description(){
        /**
         * 常用的Channel有这四种：
         *      FileChannel，读写文件中的数据。
         *      SocketChannel，通过TCP读写网络中的数据。
         *      ServerSockectChannel，监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。
         *      DatagramChannel，通过UDP读写网络中的数据。
         * Channel本身并不存储数据，只是负责数据的运输。
         * 🏁必须要和Buffer一起使用。
         *
         */
    }

    /**
     * todo FileChannel
     */
    static void fileChannel() throws Exception{
        //说明文件
        String path="/Users/lvjun/IdeaProjects/learing-netty/README.md";
        //获取文件输入流
        File file=new File(path);
        FileInputStream fileInputStream=new FileInputStream(file);
        //从文件输入流中获取管道
        FileChannel inputStreamChannel=fileInputStream.getChannel();

        //获取文件输出流
        String outPath="/Users/lvjun/IdeaProjects/learing-netty/README2.md";
        File outfile=new File(outPath);
        FileOutputStream outputStream=new FileOutputStream(outfile);
        //从文件输出流中获取管道
        FileChannel outputStreamChannel=outputStream.getChannel();

        //创建一个缓冲区
        ByteBuffer byteBuffer= ByteBuffer.allocate((int)file.length());
        //todo step-1
        //把输入管道中的数据读到缓冲区
        //inputStreamChannel.read(byteBuffer);
        //缓冲区切换成读模式
        //byteBuffer.flip();
        //把数据从缓冲区写到输出流管道中
        //outputStreamChannel.write(byteBuffer);

        //todo step-2 等效step-1
        //把输入流通道的数据读取到输出流的通道
        //transferTo()：把源通道的数据传输到目的通道中。
        //inputStreamChannel.transferTo(0, byteBuffer.limit(), outputStreamChannel);

        //todo step-3 等效step-1，step-2
        //把输入流通道的数据读取到输出流的通道
        //transferFrom()：把来自源通道的数据传输到目的通道。
        outputStreamChannel.transferFrom(inputStreamChannel,0,byteBuffer.limit());

        //资源释放处理
        outputStream.close();
        fileInputStream.close();
        outputStreamChannel.close();
        inputStreamChannel.close();
    }

    /**
     * todo 分散读取和聚合写入
     * @throws Exception
     */
    static void fileChannelGatheringOrScattering() throws Exception{
        //说明文件
        String path="/Users/lvjun/IdeaProjects/learing-netty/README.md";
        //获取文件输入流
        File file=new File(path);
        FileInputStream fileInputStream=new FileInputStream(file);
        //从文件输入流中获取管道
        FileChannel inputStreamChannel=fileInputStream.getChannel();

        //获取文件输出流
        String outPath="/Users/lvjun/IdeaProjects/learing-netty/README2.md";
        File outfile=new File(outPath);
        FileOutputStream outputStream=new FileOutputStream(outfile);
        //从文件输出流中获取管道
        FileChannel outputStreamChannel=outputStream.getChannel();

        //创建三个缓冲区，分别都是5
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(5);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(5);
        ByteBuffer byteBuffer3 = ByteBuffer.allocate(5);
        //创建一个缓冲区数组
        ByteBuffer[] buffers = new ByteBuffer[]{byteBuffer1, byteBuffer2, byteBuffer3};

        //循环写入到buffers缓冲区数组中，分散读取
        long read;
        long sumLength = 0;
        while ((read = inputStreamChannel.read(buffers)) != -1) {
            sumLength += read;
            Arrays.stream(buffers)
                    .map(buffer -> "posstion=" + buffer.position() + ",limit=" + buffer.limit())
                    .forEach(System.out::println);
            //切换模式
            Arrays.stream(buffers).forEach(Buffer::flip);
            //聚合写入到文件输出通道
            outputStreamChannel.write(buffers);
            //清空缓冲区
            Arrays.stream(buffers).forEach(Buffer::clear);
        }
        System.out.println("总长度:" + sumLength);
        //关闭通道
        outputStream.close();
        fileInputStream.close();
        outputStreamChannel.close();
        inputStreamChannel.close();
    }

    /**
     * todo 非直接/直接缓冲区的区别
     *      非直接缓冲区的创建方式：static ByteBuffer allocate(int capacity)
     *      直接缓冲区的创建方式：static ByteBuffer allocateDirect(int capacity)
     * @throws Exception
     */
    static void fileChannelDirect() throws Exception{
        long starTime = System.currentTimeMillis();
        //说明文件
        String path="/Users/lvjun/IdeaProjects/learing-netty/README.md";//todo 假设：文件大小136 MB
        //获取文件输入流
        File file=new File(path);
        FileInputStream fileInputStream=new FileInputStream(file);
        //从文件输入流中获取管道
        FileChannel inputStreamChannel=fileInputStream.getChannel();

        //获取文件输出流
        String outPath="/Users/lvjun/IdeaProjects/learing-netty/README2.md";
        File outfile=new File(outPath);
        FileOutputStream outputStream=new FileOutputStream(outfile);
        //从文件输出流中获取管道
        FileChannel outputStreamChannel=outputStream.getChannel();

        //创建一个直接缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(5 * 1024 * 1024);
        //创建一个非直接缓冲区
        //ByteBuffer byteBuffer = ByteBuffer.allocate(5 * 1024 * 1024);

        //关闭通道
        outputStream.close();
        fileInputStream.close();
        outputStreamChannel.close();
        inputStreamChannel.close();
        long endTime = System.currentTimeMillis();
        System.out.println("消耗时间：" + (endTime - starTime) + "毫秒");
    }


    /**
     * todo SocketChannel
     * @throws Exception
     */
    static void socketChannel() throws Exception{

        //获取ServerSocketChannel
        String host="127.0.0.1";
        int port=8088;
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        InetSocketAddress address=new InetSocketAddress(host,port);
        serverSocketChannel.bind(address);

        //创建一个缓冲区
        ByteBuffer byteBuffer= ByteBuffer.allocate(1024);
        while (true) {
            //获取SocketChannel
            SocketChannel socketChannel = serverSocketChannel.accept();     //todo 阻塞式的,要做到非阻塞还需要使用选择器Selector。
            while (socketChannel.read(byteBuffer) != -1){
                //打印结果
                System.out.println(new String(byteBuffer.array()));
                //清空缓冲区
                byteBuffer.clear();
            }
        }
    }

    /**
     * todo Selector选择器
     *  Selector翻译成选择器，有些人也会翻译成多路复用器，实际上指的是同一样东西。
     *  只有网络IO才会使用选择器，文件IO是不需要使用的。
     * @throws Exception
     * 选择器可以说是NIO的核心组件，它可以监听通道的状态，来实现异步非阻塞的IO。
     * 换句话说，也就是事件驱动。以此实现单线程管理多个Channel的目的。
     */
    static void selector() throws Exception{

    }



}
