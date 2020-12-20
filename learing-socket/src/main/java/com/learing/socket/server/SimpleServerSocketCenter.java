package com.learing.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * TODO 服务端
 * @Author lvjun@csdn.net
 * @Date 2020/12/20 8:32 下午
 * @Modified By:
 */
public class SimpleServerSocketCenter {

    /**
     * sokcet 服务端
     * @param args
     */
    public static void main(String[] args) throws IOException {

        System.out.println("服务启动，等待连接中");
        //1，创建ServerSocket对象，绑定端口，开始等待连接
        ServerSocket ss = new ServerSocket(8888);
        //2，接受accept方法，返回socket对象
        Socket server= ss.accept();
        //3，获取输入对象
        InputStream is=server.getInputStream();
        //4，解析客户端传入的数据
        byte[] b=new byte[1024];
        int len=is.read(b);
        String msg = new String(b,0,len);
        System.out.println(msg);

        //5，回应信息给客户端
        OutputStream os=server.getOutputStream();
        os.write("我已经收到消息了".getBytes(StandardCharsets.UTF_8));


        //n，闭资源
        is.close();
        os.close();
        server.close();
    }


}
