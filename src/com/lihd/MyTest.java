package com.lihd;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 10:19
 */
public class MyTest {
    public static void main(String[] args) {

        String s = getAny();

    }
    //可以给任意对象赋值，直接强转
    public static <T> T getAny(){

        Object o = 89;

        return (T) o;
    }
}
