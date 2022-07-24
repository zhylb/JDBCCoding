package com.lihd.preparedstatement.crud;

import com.lihd.bean.Customer;
import com.lihd.bean.Order;
import com.lihd.util.JDBCUtils;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 23:10
 */
public class QueryUtils {

    public static void main(String[] args) {
//        String sql = "select order_id  orderId,order_name orderName,order_date orderDate " +
//                " from `order` where order_id = ?";
//        Order one = queryOne(Order.class, sql, 4);
//        System.out.println(one);
//
//        sql = "select name,email,birth from customers where id = ?";
//        Customer customer = queryOne(Customer.class, sql, 10);
//        System.out.println(customer);


        String sql = "select order_id  orderId,order_name orderName,order_date orderDate " +
                " from `order` where order_id < ?";
        List<Order> orders = queryForList(Order.class, sql, 4);
        orders.forEach(System.out::println);

        sql = "select id,name,email,birth from customers where id < ?";
        List<Customer> customers = queryForList(Customer.class, sql, 10);
        customers.forEach(System.out::println);

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

    public static <T> List<T> queryForList(Class<T> clazz, String sql, Object ... args){
        ArrayList<T> list = new ArrayList<>();

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
            //通过循环 给结果集rs每个对象都赋值
            while(rs.next()){
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
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后关闭 资源
            JDBCUtils.closeResource(connection,ps,rs);
        }
        return null;
    }

}
