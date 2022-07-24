package com.lihd.jdbc.junit;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/4/22 21:33
 */
public class TestConnection {

    Connection connection;


    @Test
    public void test01() throws Exception{

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("pgsql.properties");
        Properties properties = new Properties();
        properties.load(is);

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");


        Class.forName(driver);

        connection = DriverManager.getConnection(url, username, password);
    }

    @Test
    public void test2() throws Exception{

        String sql = "select * from student";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

    }


}
