package com.learing.socket.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * TODO 处理工具类
 * @Author lvjun@csdn.net
 * @Date 2020/12/20 9:10 下午
 * @Modified By:
 */
public class ToolUtils {

    /**
     * todo 打印输入流中的信息
     * @param is 输入流
     * @return
     */
    public static void printInputStreamInfo(InputStream is) throws IOException {

        byte[] b=new byte[1024];
        int len=is.read(b);
        String msg = new String(b,0,len);
        System.out.println(msg);


//        //todo 为什么会造成阻塞呢？
//        byte[] b = new byte[1024];
//        int len=0;
//        //todo 当没有读取到客户端发送的消息时，就会阻塞到这里，InputStream.read()方法下面的代码不会执行
//        while((len = is.read(b))!=-1){
//            System.out.println(new String(b,0,len));
//        }
    }

    /**
     * todo 返回响应信息
     * @param os        响应流
     * @param responseMsg   响应信息
     * @throws IOException
     */
    public static void responseOutputStreamInfo(OutputStream os,String responseMsg) throws IOException {
        os.write(responseMsg.getBytes(StandardCharsets.UTF_8));
    }


}
