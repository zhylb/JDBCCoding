package com.lihd.batch;

import com.lihd.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 11:57
 */
public class BatchInsert {

    /**
     * Test01 : 批量插入方式一
     * 方式一 插入两千条数据 2258ms
     */
    @Test
    public void test01 ()throws Exception{
        long start = System.currentTimeMillis();
        Connection connection = JDBCUtils.getConnection();

        Statement statement = connection.createStatement();

        for (int i = 0; i < 2000; i++) {
            String sql = "insert into goods(name) values ('name" +i +"')";
            statement.execute(sql);
        }

        statement.close();
        connection.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    /**
     * Test02 : 方式二 使用PreparedStatement 替换 Statement
     * 修改 使用PreparedStatement 替换 Statement
     *
     * 插入两千条数据 2164ms
     * 似乎并没有效率提升
     *
     */
    @Test
    public void test02 () throws Exception{
        long start = System.currentTimeMillis();
        Connection connection = JDBCUtils.getConnection();

        String sql = "insert into goods(name) values (?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        for (int i = 0; i < 2000; i++) {
            ps.setObject(1,"name" + i);
            ps.execute();
        }

        ps.close();
        connection.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    /**
     * Test03 :
     * 修改1： 使用 addBatch() / executeBatch() / clearBatch()
     * 修改2：mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
     * 		 ?rewriteBatchedStatements=true 写在配置文件的url后面
     * 修改3：使用更新的mysql 驱动：mysql-connector-java-5.1.37-bin.jar
     *
     * 开启批处理之前20000 条数据14827
     * 开启批处理之后20000 条数据876
     *
     *
     */
    @Test
    public void test03 () throws Exception{
        long start = System.currentTimeMillis();
        Connection connection = JDBCUtils.getConnection();

        String sql = "insert into goods(name) values (?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        for (int i = 1; i <= 20000; i++) {
            ps.setObject(1,"name" + i);
            ps.addBatch();

            if(i % 5000 == 0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }

        ps.close();
        connection.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * Test04 : 最终版 在 方式三的基础上迭代
     * 使用Connection 的 setAutoCommit(false)  /  commit()
     *
     * 设置防止自动提交后 20000 条 593ms
     */
    @Test
    public void test04 ()throws Exception{
        long start = System.currentTimeMillis();
        Connection connection = JDBCUtils.getConnection();
        connection.setAutoCommit(false);
        String sql = "insert into goods(name) values (?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        for (int i = 1; i <= 20000; i++) {
            ps.setObject(1,"name" + i);
            ps.addBatch();

            if(i % 5000 == 0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        ps.close();
        connection.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
