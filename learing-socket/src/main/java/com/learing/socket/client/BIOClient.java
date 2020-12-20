package com.learing.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * TODO 阻塞客户端
 * @Author lvjun@csdn.net
 * @Date 2020/12/20 10:07 下午
 * @Modified By:
 *
 *  read()阻塞
 *
 */
public class BIOClient {

    /**
     * 实现一个阻塞客户端
     * @param args
     */
    public static void main(String[] args) throws IOException {

        while(true){
            //1,创建Socket
            Socket client = new Socket("127.0.0.1",8888);
            System.out.println("客户端段xiu~地一声启动成功.....");
            //2,拿到输入输出流
            InputStream is=client.getInputStream();             //阻塞
            OutputStream os=client.getOutputStream();

            //3,开始和服务端交互
            System.out.println("请输入：");
            Scanner scanner=new Scanner(System.in);
            String msg=scanner.nextLine();
            os.write(msg.getBytes(StandardCharsets.UTF_8));
            //todo 表明客户端不再发送其他消息，也即有以下现象：服务端的inputstream读了一次数据后，不会阻塞，而是可以继续执行后续代码
            client.shutdownOutput();
            //os.write("客户端你好，我是确认confirm。。。。".getBytes(StandardCharsets.UTF_8));

            //在此一直接收服务段推送的消息
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1){ //当没有读取到服务端发送的消息时，就会阻塞到这里，InputStream.read()方法下面的代码不会执行
                System.out.println("接收到信息长度:"+len);
                String resp= new String(b);
                System.out.println("服务端说：" +resp);
                //清理缓存中信息
                b=new byte[1024];
            }

            System.out.println("客户端逻辑处理完毕.....");

        }
    }

}
