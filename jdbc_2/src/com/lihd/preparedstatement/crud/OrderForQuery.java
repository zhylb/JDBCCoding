package com.lihd.preparedstatement.crud;

import com.lihd.bean.Order;
import com.lihd.util.JDBCUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 21:50
 */
public class OrderForQuery {

    /**
     * Test01 :
     * 当数据库中的列名与java类中的属性名不同时 注意：
     * 1 在查询语句中起别名
     * 2 元数据rsmd 获取列名时使用 getColumnLabel() 而不是 getColumnName()
     *
     *
     */
    @Test
    public void test01 () throws Exception{
        // 1要起别名  2 要使用get
        // String sql = "select order_id ,order_name,order_date from `order` where order_id = ?";
        String sql = "select order_id  orderId,order_name orderName,order_date orderDate " +
                " from `order` where order_id = ?";
        System.out.println(sql);
        Order one = getOne(sql, 2);
        System.out.println(one);


    }


    public Order getOne(String sql,Object ... args) throws Exception{

        Connection connection = JDBCUtils.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[0]);
        }

        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int columnCount = rsMetaData.getColumnCount();

        if(rs.next()){
            Order order = new Order();
            for (int i = 1; i <= columnCount; i++) {
                Object value = rs.getObject(i);

                String label = rsMetaData.getColumnLabel(i);
                //String label = rsMetaData.getColumnName(i);

                Field field = Order.class.getDeclaredField(label);
                field.setAccessible(true);
                field.set(order,value);


            }
            return order;
        }


        return null;
    }
}
