package com.lihd.dbutils;

import com.lihd.bean.Customer;
import com.lihd.utils.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 16:13
 */
public class MyHandlerTest {
    public static void main(String[] args) throws Exception{
        String sql = "select id,name,email,birth from customers where id = ?";

        Connection conn = JDBCUtils.getConnection();
        QueryRunner qr = new QueryRunner();

        ResultSetHandler<Customer> handler = new MyResultHandler<>(Customer.class);

        Customer customer = qr.query(conn, sql, handler, 20);
        System.out.println(customer);

        DbUtils.closeQuietly(conn);
    }
}
