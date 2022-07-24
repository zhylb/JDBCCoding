package com.lihd.connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 13:24
 */
public class DruidTest {
    /**
     * Test01 : 采用硬编码的方式
     */
    @Test
    public void test01 () throws Exception{
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:13306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("abc123");

        dataSource.setInitialSize(5);
        dataSource.setMaxActive(5);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);

    }

    /**
     * Test02 : 方式二 使用配置文件的方式
     */
    @Test
    public void test02 () throws Exception{
        //druid和DBCP好像 甚至可以共用一个配置文件
        //InputStream is = ClassLoader.getSystemResourceAsStream("Druid.properties");
        InputStream is = ClassLoader.getSystemResourceAsStream("DBCP.properties");
        Properties properties = new Properties();
        properties.load(is);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
