package com.lihd.statement.crud;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的工具类
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 19:15
 */
public class JDBCUtils {
    /**
     * 获取数据库的连接
     * @Author lihd
     * @Description 获取数据库连接
     * @Date 19:17 2022/3/29
     * @Param []
     * @return java.sql.Connection
     **/
    public static Connection getConnection() throws Exception{
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
    }
    /*
     * @Author lihd
     * @Description 关闭资源的操作
     * @Date 19:20 2022/3/29
     * @Param [conn, statement]
     * @return void
     **/
    public static void closeResource(Connection conn, Statement statement){
        try {
            if (conn != null) {

                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResource(Connection conn, Statement statement,ResultSet resultSet){
        try {
            if (conn != null) {

                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet != null) {

                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean update(String sql,Object ... args){
        // 1获取Connection
        Connection connection = null;
        PreparedStatement ps = null;
        boolean execute = false;
        try {
            connection = getConnection();
            //2 预编译sql,返回ps对象
            ps = connection.prepareStatement(sql);
            //3 设置参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }
            //4 执行
            execute = ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(connection,ps);
        }
        //5 关闭流
        return execute;

    }
    
    
}
