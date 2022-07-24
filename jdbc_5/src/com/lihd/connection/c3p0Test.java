package com.lihd.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 11:15
 */
public class c3p0Test {
    /**
     * Test01 : 获取连接方式一 quickStart
     */
    @Test
    public void test01 () throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:mysql://localhost:13306/test" );
        cpds.setUser("root");
        cpds.setPassword("abc123");

        cpds.setMaxConnectionAge(20);

        Connection connection = cpds.getConnection();
        System.out.println(connection);
        //销毁数据库 连接池 一般不这么做
        DataSources.destroy( cpds );
    }

    /**
     * Test02 : 方式二 使用xml
     *
     *
     *
     */
    @Test
    public void test02 () throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloC3P0");

        Connection connection = cpds.getConnection();

        System.out.println(connection);


    }

}
