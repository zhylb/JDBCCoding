package com.lihd.preparedstatement.crud;

import com.lihd.bean.Customer;
import com.lihd.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/29 21:17
 */
public class CustomerForQuery {

    /**
     * Test02 :
     */
    @Test
    public void test02 (){
        String sql = "select id,name,email,birth from customers where id = ?";
        sql = "select id,name from customers where id = ?";
        Customer customer = queryForCustomers(sql, 2);
        System.out.println(customer);
    }

    public Customer queryForCustomers(String sql,Object ... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            // 1获取Connection
            connection = JDBCUtils.getConnection();
            // 2
            ps = connection.prepareStatement(sql);
            //3 设置参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            //4 执行 eq
            resultSet = ps.executeQuery();
            //5 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //6 通过元数据获取column
            int columnCount = metaData.getColumnCount();
            if(resultSet.next()){
                Customer customer = new Customer();
                // 一行一行的处理
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);
                    // 给cust对象指定的某个属性赋值为value

                    String columnName = metaData.getColumnName(i + 1);

                    // 通过反射赋值 难度比较大
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(customer,value);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7 关闭
            JDBCUtils.closeResource(connection,ps,resultSet);
        }
        return null;

    }


    /**
     * Test01 :
     */
    @Test
    public void test01 () {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            // 1获取connection
            connection = JDBCUtils.getConnection();
            // 2 预编译sql 返回ps实例
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = connection.prepareStatement(sql);
            // 3 填充占位符
            ps.setObject(1,1);
            // 4 执行sql 查询使用executeQuery()
            resultSet = ps.executeQuery();
            //resulSet.next() 如果有下一个元素返回true并且指针下移，如果没有下一个元素返回false并且结束
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps,resultSet);
        }

        // 5 关闭流

    }

}
