package com.learing.socket.client;

import com.learing.socket.utils.ToolUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * TODO 2号客户端
 * @Author lvjun@csdn.net
 * @Date 2020/12/20 9:38 下午
 * @Modified By:
 */
public class SimpleClientSocket2 {
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
        os.write("客户端B请求接入....".getBytes());

        //4，获取服务端反馈的信息
        InputStream is=client.getInputStream();
        ToolUtils.printInputStreamInfo(is);


        //n，关闭资源
        is.close();
        os.close();
        client.close();

    }
}
