package com.lihd.dao1;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 9:09
 */
public abstract class BaseDAO<T> {

    private Class<T> clazz;


    //在代码块中赋予初值
    {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType parType = (ParameterizedType) type;
        Type[] actualTypeArguments = parType.getActualTypeArguments();
        clazz = (Class<T>) actualTypeArguments[0];
    }



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

    public  <E> E valueQuery(Connection conn,String sql,Object...args) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                E e = (E) rs.getObject(1);
                return e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return null;

    }


    public int updateQuery(Connection conn,String sql,Object...args){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            int i = ps.executeUpdate();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return 0;
    }


    public  T queryForOne(Connection conn, String sql, Object ... args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1 获取connection
            conn = getConnection();
            //2 根据connection预编译sql命令，返回preparedStatement对象
            ps = conn.prepareStatement(sql);
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
            close(ps,rs);
        }


        return null;
    }

    public  List<T> queryForList(Connection conn,  String sql, Object ... args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();
        try {
            //1 获取connection
            conn = getConnection();
            //2 根据connection预编译sql命令，返回preparedStatement对象
            ps = conn.prepareStatement(sql);
            //3 ps对象设置参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()){
                T t =  clazz.newInstance();

                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);

                    String columnLabel = rsmd.getColumnLabel(i);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,value);
                }

                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps,rs);
        }


        return null;
    }



    public static void close(Connection conn){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement statement){
        close(statement,null);
    }


    public static void close(Statement statement, ResultSet rs){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs != null) {

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
