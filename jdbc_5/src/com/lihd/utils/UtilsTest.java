package com.lihd.utils;

import java.sql.Connection;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 13:41
 */
public class UtilsTest {
    public static void main(String[] args) throws Exception{
        Connection connection = JDBCUtils.getConnection();
        Connection connectionByC3P0 = JDBCUtils.getConnectionByC3P0();
        Connection connectionByDBCP = JDBCUtils.getConnectionByDBCP();
        Connection connectionByDruid = JDBCUtils.getConnectionByDruid();

        System.out.println("connection = " + connection);
        System.out.println("connectionByC3P0 = " + connectionByC3P0);
        System.out.println("connectionByDBCP = " + connectionByDBCP);
        System.out.println("connectionByDruid = " + connectionByDruid);

    }
}
