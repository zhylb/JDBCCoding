package com.lihd.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 15:33
 */
public class ConnectionTest {

    /**
     * TestConnection1 : 方式一
     * 快捷键 ctrl + H 查看一个类的树形结构 试着看一下Driver
     */
    @Test
    public void testConnection1 () throws SQLException {
        //获取driver的实现类对象
        Driver driver = new com.mysql.jdbc.Driver();
        // jdbc:mysql 协议
        // localhost:ip地址
        // 13306 端口号
        // test:test数据库
        String url = "jdbc:mysql://localhost:13306/test";
        //封装用户名和密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","abc123");
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }
    
    /**
     * Test02 : 方式二 对方式一的迭代
     * 不要出现第三方的new xxx 使程序更具备移植性
     *
     *
     */
    @Test
    public void test02 () throws Exception{
        // 通过反射获取要连接的数据库
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        // 2 提供要连接的数据库
        String url = "jdbc:mysql://localhost:13306/test";
        //3 提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","abc123");
        Connection connection = driver.connect(url, info);
        System.out.println(connection);

    }

    /**
     * Test03 :
     * 使用DriverManager 替换 Driver
     *
     *
     */
    @Test
    public void test03 () throws Exception{
        //0 准备工作
        String url = "jdbc:mysql://localhost:13306/test";
        String user = "root";
        String password = "abc123";
        //1 获取driver对象
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //2 注册驱动
        DriverManager.registerDriver(driver);

        //3 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }

    /**
     * Test04 : 在方式三的情况下进一步迭代
     */
    @Test
    public void test04 () throws Exception{
        //0 准备工作
        String url = "jdbc:mysql://localhost:13306/test";
        String user = "root";
        String password = "abc123";
        //1 获取Driver示例 为什么没有注册驱动呢
        //因为Class.forName()会把一个类加载到内存中 而com.mysql.jdbc.driver下的类只有空参构造器和静态代码块
        // 静态代码块随着类的加载而执行 因此 DriverManager.registerDriver(new Driver());被执行
        // 因此并不是没有注册驱动，而是隐式的帮我们注册了驱动
        /*
        com.mysql.jdbc
        public class Driver extends NonRegisteringDriver implements java.sql.Driver {
            static {
                try {
                    DriverManager.registerDriver(new Driver());
                } catch (SQLException var1) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
        }
         */
        Class.forName("com.mysql.jdbc.Driver");
        /* 上面的代码甚至也可以省略，这是因为
        * mysql驱动下的META-INF/services/java.sql.Driver中存放了 com.mysql.jdbc.Driver这行字符串
        * 并且会以某种方式加载进来，
        * 但是强烈不建议省略这一行代码 会降低代码可读性
        *
        */
        //2 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }


    /**
     * Test05 : 最终版
     * 好处
     * 1 实现了代码和数据的解耦
     * 2 修改配置文件不需要重新打包
     *
     */
    @Test
    public void test05 () throws Exception{
        //1 读取配置文件信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2 注册驱动
        Class.forName(driverClass);
        //3 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);


    }


}
