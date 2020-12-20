package com.learing.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * TODO 客户端
 * @Author lvjun@csdn.net
 * @Date 2020/12/20 8:34 下午
 * @Modified By:
 */
public class SimpleClientSocket {
    /**
     * socket 客户端
     * @param args
     */
    public static void main(String[] args) throws IOException {

        System.out.println("客户端发送数据");
        //1，创建Socket
        Socket client = new Socket("127.0.0.1",8888);
        //2，输出流
        OutputStream os = client.getOutputStream();
        //3，写出数据
        os.write("连接服务器".getBytes());

        //4，获取服务端反馈的信息
        InputStream is=client.getInputStream();
        byte[] b = new byte[1024];
        int len = is.read(b);
        System.out.println(new String(b,0,len));


        //n，关闭资源
        is.close();
        os.close();
        client.close();

    }


}
