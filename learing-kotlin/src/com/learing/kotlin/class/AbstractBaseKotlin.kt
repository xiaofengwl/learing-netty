package com.learing.kotlin.`class`

/**
 * TODO 抽象是面向对象编程的特征之一，类本身，或类中的部分成员，都可以声明为abstract的。抽象成员在类中不存在具体的实现。
 * TODO 注意：无需对抽象类或抽象成员标注open注解。
 * @Author lvjun@csdn.net
 * @Date 2021/2/8 3:05 下午
 * @Modified By:
 *
abstract    // 抽象类
final       // 类不可继承，默认属性
enum        // 枚举类
open        // 类可继承，类默认是final的
annotation  // 注解类
 */
open class AbstractBaseKotlin {

    /**
     * todo 定义一个抽象方法
     */
    open fun abstractMethod() {
        println("AbstractBaseKotlin:open fun abstractMethod()")
    }
}

/**
 * todo 抽象方法的实现子类
 */
abstract class Derived : AbstractBaseKotlin() {
    /**
     * todo 重写抽象类中的抽象方法
     */
     abstract override fun abstractMethod()
}