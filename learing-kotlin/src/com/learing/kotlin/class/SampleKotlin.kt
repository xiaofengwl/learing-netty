package com.learing.kotlin.`class`

/**
 *
 * @Author lvjun@csdn.net
 * @Date 2021/2/8 9:43 上午
 * @Modified By:
 */
class SampleKotlin constructor(name:String) {

    /**
     * todo 定义常量
     */
    var count:Int=1;
    var str:String="kotlin";
    val finalStr:String="FINAL_COMMON_STRING";
    var sum=1;

    /**
     * 主构函数
     */
    init {
        println("SampleKotlin init ....."+name)
    }

    /**
     * todo 创建一个函数，没有指定返回值
     */
    fun test(first:Int,second:Int,bool:Boolean):Unit{
        if(bool){
            println(first+second)
        }else{
            println(first-second)
        }
    }
    /**
     * todo 创建一个函数，指定返回值
     */
    fun test2(first:Int,second:Int,bool:Boolean):Int{
        if(bool){
            return (first+second)
        }else{
            return (first-second)
        }
    }

    fun test3(){
        var a = 1
        // 模板中的简单名称：
        val s1 = "a is $a"
        println(s1)

        a = 2
        // 模板中的任意表达式：
        val s2 = "${s1.replace("is", "was")}, but now is $a"
        println(s2)
    }

}
