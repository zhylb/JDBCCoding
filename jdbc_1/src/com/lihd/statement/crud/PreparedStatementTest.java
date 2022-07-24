package com.lihd.statement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * 使用PreparedStatement 可以完全取代 Statement;
 * 1 无需拼串 可读性高
 * 2 预先编译 无法sql注入 提高安全性
 * 3 预先编译 可以处理Blob类型
 * 4 预选编译 插入大量数据时效率高
 * 总之 可以使用Statement的子接口完全取代PreparedStatement;
 *
 *
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 23:34
 */
public class PreparedStatementTest {
    public static void main(String[] args) {


        // 下面的实验证明 sql确实是预先编译过的，无法实现sql注入
        // sql 注入 用户名密码尝试使用一下输入
        //username = 1 ' or
        //password = or '1' = '1
		/* 这样到sql 语句中就会编程以下情况
		SELECT user,password
		FROM user_table
		WHERE USER = '1' OR ' and password = 'OR '1' = '1'
		* */
        String username = "1 ' or";
        String password = "or '1' = '1";
//        String username = "AA";
//        String password = "123456";

        String sql = "SELECT user, password FROM user_table WHERE user = ? AND password = ?";

        User user = queryOne(User.class,sql,username,password);
        System.out.println(user);


    }


    public static <T> T queryOne(Class<T> clazz,String sql,Object ... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1 获取connection对象
            connection = JDBCUtils.getConnection();
            //2 预编译sql 返回ps对象
            ps = connection.prepareStatement(sql);
            //3 设置参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //4 执行 executeQuery 获取结果集rs
            rs = ps.executeQuery();
            //5 结果集rs 获取 结果集元数据 rsmd
            ResultSetMetaData rsmd = rs.getMetaData();
            //6 元数据获取行数
            int columnCount = rsmd.getColumnCount();
            //
            if(rs.next()){
                //创建对象
                T t = clazz.newInstance();
                //遍历结果集 每一个 字段
                for (int i = 1; i <= columnCount; i++) {
                    //获取第 i 个字段的值
                    Object value = rs.getObject(i);
                    //获取第 i 个字段对应的别名 由于使用了getColumnLable因此时别名
                    String columnLabel = rsmd.getColumnLabel(i);
                    //通过反射 ，设置可见性为真，获取别名对应的java属性，进而赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,value);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后关闭 资源
            JDBCUtils.closeResource(connection,ps,rs);
        }
        return null;
    }

}
