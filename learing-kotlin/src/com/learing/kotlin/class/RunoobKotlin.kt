package com.learing.kotlin.`class`

/**
 *
 * @Author lvjun@csdn.net
 * @Date 2021/2/8 2:49 下午
 * @Modified By:
 */
class RunoobKotlin constructor(var param1:String ,var param2:String){

    // 大括号内是类体构成
    var url: String =param2;
    var country: String = "CN"
    var siteName = param1
    /**
     * todo 主构函数
     */
    init {
        println("初始化网站名: ${siteName}-${url}")
    }

    fun printTest() {
        println("我是类的函数")
    }
}

class CakeKotlin{
    /**
     * 次构函数
     */
    constructor(parent:RunoobKotlin){
        println("CakeKotlin:"+parent.siteName+"-"+parent.url);
    }
}

/**
 * todo 测试
 */
fun main(args: Array<String>) {
    val runoob =  RunoobKotlin("菜鸟教程","http://www.runoob.com")
    val runoob2 =  RunoobKotlin("小风微凉大白","https://www.zhihu.com/people/xiao-feng-wei-liang-8")
    println(runoob.siteName)
    println(runoob.url)
    println(runoob.country)
    runoob.printTest()
    val runoob3=CakeKotlin(runoob);

}