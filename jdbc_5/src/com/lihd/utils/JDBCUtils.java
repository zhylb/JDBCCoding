package com.lihd.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 13:34
 */
public class JDBCUtils {

    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloC3P0");
    private static BasicDataSource DBCPDataSource;
    private static DruidDataSource druidDataSource;

    static{
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream("DBCP.properties");
            Properties properties = new Properties();
            properties.load(is);
            DBCPDataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream("Druid.properties");
            Properties properties = new Properties();
            properties.load(is);
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnectionByC3P0() throws SQLException {
        return cpds.getConnection();
    }

    public static Connection getConnectionByDBCP() throws SQLException {
        return DBCPDataSource.getConnection();
    }

    public static Connection getConnectionByDruid() throws SQLException {
        return druidDataSource.getConnection();
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

    public static <T> T valueQuery(Connection conn,String sql,Object...args) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                T t = (T) rs.getObject(1);
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }
        return null;

    }


    public static int updateQuery(Connection conn,String sql,Object...args){
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


    public static <T> T queryForOne(Connection conn,Class<T> clazz, String sql, Object ... args){
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

    public static <T> List<T> queryForList(Connection conn, Class<T> clazz, String sql, Object ... args){
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
                T t = clazz.newInstance();

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
