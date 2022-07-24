package com.lihd.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 13:08
 */
public class DBCPTest {
    /**
     * Test01 : 方式一 硬编码的方式
     */
    @Test
    public void test01 ()throws Exception{
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:13306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("abc123");

        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }


    /**
     * Test02 : 使用配置文件
     *
     *
     *
     */
    @Test
    public void test02 () throws Exception{
        InputStream is = ClassLoader.getSystemResourceAsStream("DBCP.properties");
        Properties properties = new Properties();
        properties.load(is);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);


    }
}
