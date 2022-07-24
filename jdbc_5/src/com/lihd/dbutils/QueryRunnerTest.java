package com.lihd.dbutils;

import com.lihd.bean.Customer;
import com.lihd.utils.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;
import sun.plugin2.main.server.ResultHandler;

import javax.management.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 15:16
 */
public class QueryRunnerTest {
    /**
     * Test01 : 实现添加一条记录
     */
    @Test
    public void test01 (){

        Connection con = null;
        try {
            con = JDBCUtils.getConnectionByDruid();
            QueryRunner qr = new QueryRunner();
            String sql = "insert into customers(name,email,birth) values(?,?,?)";
            qr.update(con,sql,"冲鸭","happy@notsad.com","2022-03-31");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(con);
        }
    }

    /**
     * Test02 : 查询一条数据 为了提高可读性 不再异常处理
     * 使用 BeanHandler<Bean>(Bean.class) 查询一条数据 返回Bean
     */
    @Test
    public void test02 () throws Exception{
        String sql = "select id,name,email,birth from customers where id = ?";
        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();

        ResultSetHandler<Customer> handler = new BeanHandler<Customer>(Customer.class);

        Customer customer = qr.query(conn, sql, handler, 24);
        System.out.println(customer);

        DbUtils.closeQuietly(conn);

    }


    /**
     * Test03 : 查询多条数据
     * 使用 BeanListHandler<Bean>(Bean.class) 查询多条数据 封装为 List<Bean>
     *
     *
     */
    @Test
    public void test03 () throws Exception{
        String sql = "select id,name,email,birth from customers where id < ?";
        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();
        BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
        List<Customer> query = qr.query(conn, sql, handler, 24);
        query.forEach(System.out::println);

        DbUtils.closeQuietly(conn);
    }

    /**
     * Test04 :
     * ArrayHandler 将一条 数据封装为 Object[]
     */
    @Test
    public void test04 () throws Exception{
        String sql = "select id,name,email,birth from customers where id = ?";
        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();
        ArrayHandler handler = new ArrayHandler();

        Object[] query = qr.query(conn, sql, handler, 24);
        System.out.println(Arrays.toString(query));
        DbUtils.closeQuietly(conn);
    }

    /**
     * Test05 :
     * ArrayListHandler 将多条 数据封装为  List<Object[]>
     * 这里的ArrayList指的是 Array 组合成的 list
     *
     */
    @Test
    public void test05 () throws Exception{
        String sql = "select id,name,email,birth from customers where id < ?";
        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();
        ArrayListHandler handler = new ArrayListHandler();
        List<Object[]> list = qr.query(conn, sql, handler, 24);

        list.stream().forEach(a -> System.out.println(Arrays.toString(a)));


        DbUtils.closeQuietly(conn);
    }

    /**
     * Test06 :
     * MapHandler 将 一条数据 封装为 map 返回
     * 其中 key 是String 也就是 字段名
     * 而 value 是字段名对应的值 比如
     * {name=冲鸭, birth=2022-03-31, id=24, email=happy@notsad.com}
     *
     */
    @Test
    public void test06 () throws Exception{
        String sql = "select id,name,email,birth from customers where id = ?";
        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();
        MapHandler mapHandler = new MapHandler();

        Map<String, Object> query = qr.query(conn, sql, mapHandler, 24);
        System.out.println(query);
        DbUtils.closeQuietly(conn);
    }

    /**
     * Test07 :
     * MapListHandler 将 一条数据 封装为 List<map<String,Object>> 返回
     * 其中 key 是String 也就是 字段名
     * 而 value 是字段名对应的值
     *
     */
    @Test
    public void test07 () throws Exception{
        String sql = "select id,name,email,birth from customers where id < ?";
        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();
        MapListHandler handler = new MapListHandler();
        List<Map<String, Object>> list = qr.query(conn, sql, handler, 24);
        list.forEach(System.out::println);
        DbUtils.closeQuietly(conn);
    }

    /**
     * Test08 : 查询其他值
     *  ScalarHandler
     *
     *
     */
    @Test
    public void test08 () throws Exception{
        String sql = "select count(*) from customers";
        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();
        ScalarHandler handler = new ScalarHandler();

        long count = (long) qr.query(conn, sql, handler);

        System.out.println(count);

        DbUtils.closeQuietly(conn);
    }

}
