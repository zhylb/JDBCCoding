package com.lihd.preparedstatement.crud;

import com.lihd.connection.ConnectionUtils;
import com.lihd.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用PreparedStatement 替换 Statement ,实现对数据表的增删改差操作
 * 增删改 ：
 * 查 ：比较复杂
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 17:48
 */
public class PreparedStatementUpdateTest {
    /**
     * Test01 : 向customers 插入一条数据
     *
     */
    @Test
    public void test01 ()throws Exception{
        //1 获取connection对象
        Connection connection = ConnectionUtils.getConnection();
        System.out.println(connection);
        // 2 预编译sql语句返回 ps实例
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        assert connection != null;
        PreparedStatement ps = connection.prepareStatement(sql);
        //3 填充占位符
        ps.setString(1,"哪吒");
        ps.setString(2,"nezah@gmail.com");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse("1000-01-01");
        ps.setDate(3,new java.sql.Date(parse.getTime()));

        // 4 执行sql
        ps.execute();

        // 5 关闭连接
        ps.close();
        connection.close();


    }

    /**
     * Test02 : 修改customers表的一条数据
     */
    @Test
    public void test02 ()throws Exception{
        //1 获取数据库
        Connection connection = JDBCUtils.getConnection();
        //2 预编译sql,返回ps实例
        String sql = "update customers set name = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        //3 填充占位符
        ps.setObject(1,"莫扎特");
        ps.setObject(2,18);
        //4 执行
        ps.execute();
        //5 资源的关闭
        JDBCUtils.closeResource(connection,ps);
    }


    /**
     * Test03 :
     *
     *
     *
     */
    @Test
    public void test03 (){
        String sql = "delete from customers where id = ?";
        boolean update = JDBCUtils.update(sql, 3);
        System.out.println(update);
    }

}
