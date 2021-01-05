package com.learing.basic;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/3 7:55 下午
 * @Modified By:
 */
public class MainTest {
    public static void main(String[] args) {
        System.out.println("cpongo1".toString().hashCode()%128);  //61-
        System.out.println("csdn".toString().hashCode()%128);     //122-
        System.out.println("cpongo1-csdn".toString().hashCode()%128);  //118-
        System.out.println("csdn-cpongo1".toString().hashCode()%128);  //106-



        System.out.println("cpongo1".toString().hashCode()%128);  //61-
        System.out.println("guyuealian".toString().hashCode()%128);     //16-
        System.out.println("cpongo1-guyuealian".toString().hashCode()%128);  //96-
        System.out.println("guyuealian-cpongo1".toString().hashCode()%128);  //0-

        String fromUsername ="cpongo1";
        String toUsername = "guyuealian";
        System.out.println(fromUsername.compareTo(toUsername));
        String sessionKey = fromUsername.compareTo(toUsername) > 0 ? toUsername + "-" + fromUsername : fromUsername + "-" + toUsername;
        System.out.println(sessionKey);
        fromUsername="guyuealian";
        toUsername="cpongo1";
        System.out.println(fromUsername.compareTo(toUsername));
        String sessionKey2 = fromUsername.compareTo(toUsername) > 0 ? toUsername + "-" + fromUsername : fromUsername + "-" + toUsername;
        System.out.println(sessionKey2);


        System.out.println("cpongo1".toString().hashCode()%128);  //61-
        System.out.println("lvjun".toString().hashCode()%128);     //16-
        System.out.println("cpongo1-lvjun".toString().hashCode()%128);  //96-
        System.out.println("lvjun-cpongo1".toString().hashCode()%128);  //0-


    }
}
