package com.lihd.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * orm思想
 * 一个表对应一个类
 * 一条结果对应一个对象
 * 一个属性对应一个属性
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 15:33
 */
public class ConnectionUtils {

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
    public static Connection getConnection (){
        try{
            //1 读取配置文件信息
            InputStream is = ClassLoader.getSystemResourceAsStream("jdbc.properties");

            Properties properties = new Properties();
            properties.load(is);
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            //2 注册驱动
            Class.forName(driverClass);
            //3 获取连接
            return DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
