package com.learing.basic.netty.protocol;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 定制消息头
 * @Author lvjun@csdn.net
 * @Date 2021/3/7 12:59 下午
 * @Modified By:
 */
@Data
public class Header {

    private int crcCode=0xadaf0105;          // 唯一的通信标志
    private int length;                      // 总消息的长度 header + body
    private long sessionID;                  // 会话ID
    private byte type;                       // 消息的类型
    private byte priority;                   // 消息的优先级 0~255
    private Map<String, Object> attachment = new HashMap<String, Object>(); // 附件


}
