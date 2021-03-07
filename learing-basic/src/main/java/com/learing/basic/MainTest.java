package com.learing.basic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/3 7:55 下午
 * @Modified By:
 */
public class MainTest {
    public static void main(String[] args) {

        JSONObject jsonObject=new JSONObject();
        String tc="你的CSDN问答VIP将于1天后到期。会员到期，5次有问必答机会、购书95折等权益失效。会员有效期内续费，权益可叠加 。立即续费 >> <a href='https://t.csdnimg.cn/pBWk'>https://t.csdnimg.cn/pBWk</a>";
        //String tc="你的CSDN问答VIP将于1天后到期。会员到期，5次有问必答机会、购书95折等权益失效。会员有效期内续费，权益可叠加 。立即续费 >> : <a href='https://mp.csdn.net/poster/20210208'>https://t.csdnimg.cn/pBWk</a>";
        jsonObject.put("tc",tc);
        if (tc.contains("<a") && tc.contains("/a") &&
                !jsonObject.containsKey("title") &&
                !jsonObject.containsKey("url")) {
            String preStr = tc.substring(0, tc.indexOf("<a"));
            String afterStr = tc.substring(tc.indexOf("<a"));
            String url = StringUtils.substringBetween(afterStr, "href=\"", "\"");
            if(null == url){
                url = StringUtils.substringBetween(afterStr, "href=\'", "\'");
            }
            String title = StringUtils.substringBetween(afterStr, ">", "<");
            if(null != title) {
                afterStr = afterStr.substring(afterStr.indexOf(title) + title.length());
            }
            tc = preStr + "{%title%}" + "{%url%}" +afterStr.substring(afterStr.indexOf(">") + 1);
            jsonObject.put("tc", tc);
            jsonObject.put("title", title);
            jsonObject.put("url", url);
        }
        System.out.println(jsonObject);

    }
}
