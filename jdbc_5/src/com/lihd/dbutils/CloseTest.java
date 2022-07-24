package com.lihd.dbutils;

import com.lihd.utils.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;

/**
 * DbUtils下也提供了 关闭操作 至此 获取连接 操作连接 关闭连接都有了可以取代的方案
 * 两个方法 和自己写的没有什么区别
 * public static void close(Connection conn) throws SQLException {
 *         if (conn != null) {
 *             conn.close();
 *         }
 * }
 *
 * public static void closeQuietly(Connection conn) {
 *         try {
 *             close(conn);
 *         } catch (SQLException var2) {
 *         }
 * }
 *
 *
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 16:17
 */
public class CloseTest {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            DbUtils.close(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        DbUtils.closeQuietly(connection);

    }
}
