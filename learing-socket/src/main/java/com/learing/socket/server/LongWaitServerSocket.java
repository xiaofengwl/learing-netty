package com.learing.socket.server;

import com.learing.socket.utils.ToolUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * TODO 长时间等待的服务端
 * @Author lvjun@csdn.net
 * @Date 2020/12/20 9:05 下午
 * @Modified By:
 */
public class LongWaitServerSocket {
    /**
     * 阻塞服务端
     * @param args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("服务启动，等待连接中");
        //1，创建ServerSocket对象，绑定端口，开始等待连接
        ServerSocket ss = new ServerSocket(8888);
        //2，接受accept方法，返回socket对象
        Socket server=null;
        int connectCount=0;
        while(Objects.nonNull(server=ss.accept())){
            System.out.println("有新的请求接入："+connectCount++);
            //3，读取客户端传入的数据
            InputStream is=server.getInputStream();
            ToolUtils.printInputStreamInfo(is);
            //4，给予接入方反馈
            OutputStream os=server.getOutputStream();
            ToolUtils.responseOutputStreamInfo(os,"已经收到,time:"+System.currentTimeMillis());
            System.out.println("已经回复");
            //5，关闭链接
            is.close();
            os.close();
            server.close();
        }

        //n，关闭流
        ss.close();
    }

}
