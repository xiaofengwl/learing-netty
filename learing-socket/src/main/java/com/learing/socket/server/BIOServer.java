package com.learing.socket.server;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * TODO 阻塞服务端
 * @Author lvjun@csdn.net
 * @Date 2020/12/20 10:14 下午
 * @Modified By:
 *
 *  TODO 总结
 *  accept()阻塞
 *  read()阻塞
 *  write()不阻塞
 *  shutdownOutput() 解除socket的对应端read()阻塞
 *
 */
public class BIOServer {

    /**
     * 阻塞服务端
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //1,创建服务端
        ServerSocket serverSocket=new ServerSocket(8888);
        System.out.println("服务吭哧吭哧...启动成功.....");
        //持续在这里等待客户端的接入：
        while(true){
            //2,监听客户端的请求
            Socket server=serverSocket.accept();//当没有客户端连接时，就会阻塞到这里，一直等待客户端的连接请求，下面的代码不会执行
            String clientIP = server.getInetAddress().getHostAddress();
            System.out.println(clientIP+"接入成功");
            //3，获取输入输出流
            InputStream is = server.getInputStream(); //阻塞
            OutputStream os = server.getOutputStream();

            //4，开始和客户端交互
            //发送1
            os.write(("已经收到您"+clientIP+"的接入请求：").getBytes(StandardCharsets.UTF_8));
            //读取1
            byte[] b = new byte[1024];
            int len;
           /**
             * todo 发现一个问题
             * todo 服务段的while-read  写法，只会阻塞监听客户端的推送信息一次，然后直接执行while后面的代码
             */
            while ((len = is.read(b)) != -1){ //当没有读取到客户端发送的消息时，就会阻塞到这里，InputStream.read()方法下面的代码不会执行
                System.out.println(clientIP + "说：" + new String(b,0,len));
            }
            System.out.println("-------服务端准备发布消息-------");   //该语句在客户端没有发送消息前不会执行，只有上面的read()方法接收到服务端发送的消息时才会执行该语句
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("请输入：退出请输入：E/e");
                String msg = scanner.nextLine();
                if(msg.equalsIgnoreCase("e")){
                    System.out.println("exit ok");
                    //通过：shutdownOutput()方法可以通知对方输入流不再阻塞，可以继续执行后续代码了。
                    server.shutdownOutput();
                    break;
                }
                os.write(msg.getBytes());
                System.out.println("send ok!");
            }
            System.out.println("服务端逻辑处理完毕...");
        }
    }


}
