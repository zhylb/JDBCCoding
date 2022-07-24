package com.lihd.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 9:59
 */
public class JDBCUtils {

    public static Connection getConnection()throws Exception{
        //1 读取配置文件信息
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream("jdbc.properties");
        properties.load(is);
        //2 获取每个信息
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        //3 注册驱动
        Class.forName(driverClass);
        //4 获取connection
        return DriverManager.getConnection(url, user, password);
    }

    public static int updateQuery(String sql,Object ... args){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();

            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            int i = ps.executeUpdate();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection,ps);
        }
        return 0;
    }


    public static <T> T queryForOne(Class<T> clazz, String sql, Object ... args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1 获取connection
            connection = getConnection();
            //2 根据connection预编译sql命令，返回preparedStatement对象
            ps = connection.prepareStatement(sql);
            //3 ps对象设置参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if(rs.next()){
                T t = clazz.newInstance();

                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);

                    String columnLabel = rsmd.getColumnLabel(i);


                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,value);
                }

                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection,ps,rs);
        }


        return null;
    }


    public static void close(Connection connection, Statement statement){
        try {
            if (connection != null) {
                connection.close();

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


    public static void close(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if (connection != null) {
                connection.close();

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




}
