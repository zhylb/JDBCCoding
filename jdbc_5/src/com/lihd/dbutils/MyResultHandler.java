package com.lihd.dbutils;


import com.lihd.bean.Customer;
import org.apache.commons.dbutils.ResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 自定义一个类似于 BeanHandler的 ResultSetHandler
 * 通过自定义的方式发现了为什么 BeanHandler还需要再传入一个 Class对象
 * 收获很多 也是对前面内容的复习
 *
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 16:00
 */
public class MyResultHandler<T> implements ResultSetHandler<T>{
    private Class<T> clazz;

    public MyResultHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handle(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if(resultSet.next()){
            T t = null;
            try {
                t = clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);

                    String columnLabel = rsmd.getColumnLabel(i);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,value);
                }
            } catch (Exception e) {
                return null;
            }
            return t;
        }
        return null;
    }
}
