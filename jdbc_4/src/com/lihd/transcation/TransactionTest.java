package com.lihd.transcation;

import com.lihd.bean.User;
import com.lihd.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 1什么是事务 一组逻辑操作单元,使数据从一种状态变换到另一种状态。
 * 2 事务处理（事务操作）：
 * 保证所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式。
 * 当在一个事务中执行多个操作时，要么所有的事务都被提交(commit)，那么这些修改就永久地保存下来；
 * 要么数据库管理系统将放弃所作的所有修改，整个事务回滚(rollback)到最初状态。
 * 3 事务提交后就不能回滚，回滚会恢复到最近的一次提交commit
 * 4 什么事件会导致事务提交
 *      1 DDL操作 事务会自动提交 无法通过set autocommit = false 阻止其自动提交
 *      2 DML操作 事务默认会提交  可以通过 set autocommit = false 阻止其自动提交
 *      3 关闭连接时，如果有事务没有提交，则会在关闭前自动提交（问题所在，要在这里优化处理）
 * 5 方法 conn.setAutoCommit(false); conn.setAutoCommit(true);
 *      建议在设置自动提交为false后，记得还原为true,对于本例没有影响
 *      但是对于 数据库连接会有影响，因此建议记得成对使用，不容易忘记
 * 6 conn.commit() 提交  conn.rollback()回滚
 *
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 14:56
 */
public class TransactionTest {


    /**
     * Test04 : 演示四种隔离级别，读进程
     *
     *
     *
     */
    @Test
    public void test04 () throws Exception{
        Connection connection = JDBCUtils.getConnection();
        //隔离级别 read uncommitted
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        connection.setAutoCommit(false);
        String sql = "select user,password,balance from user_table where user = ?";
        User cc = JDBCUtils.queryForOne(User.class,connection, sql, "CC");
        System.out.println(cc);


    }


    /**
     * Test03 : 演示四种隔离级别：写进程
     */
    @Test
    public void test03 () throws Exception{
        Connection connection = JDBCUtils.getConnection();

        connection.setAutoCommit(false);
        System.out.println(connection.getTransactionIsolation());
//        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        String sql = "update user_table set balance = 7000 where user = ?";
        JDBCUtils.updateQuery(connection,sql,"CC");


        test02();

        Thread.sleep(5_000);

        test02();





    }



    /**
     * Test02 : 使用事务进行模拟转账
     * 修改一 修改之前的通用方法，把connection作为参数传入，方法结束时不要关闭conn
     *
     */
    @Test
    public void test02 (){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            // 这里记得要 把conn作为参数传入
            String sql = "update user_table set balance = balance - 100 where user = ?";
            JDBCUtils.updateQuery(conn,sql,"AA");
            System.out.println("发生了网络错误 ！" );
            System.out.println(1/0);
            String sql1 = "update user_table set balance = balance + 100 where user = ?";
            JDBCUtils.updateQuery(conn,sql1,"BB");

            System.out.println("转账成功");
            //没有发生异常，提交
            conn.commit();


            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            //发生异常，回滚数据 记得异常处理
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCUtils.close(conn,null);
        }

    }



    /**
     * Test01 : 使用事务前 模拟转账操作
     * AA 给 BB 转账100元
     *
     * update user_table set balance = balance - 100 where user = AA
     * 如果这里发生了异常该怎么办
     * update user_table set balance = balance + 100 where user = BB
     *
     *
     */
    @Test
    public void test01 (){
        String sql = "update user_table set balance = balance - 100 where user = ?";
        JDBCUtils.updateQuery(sql,"AA");
        System.out.println("发生了网络错误 ！" );
        System.out.println(1/0);
        String sql1 = "update user_table set balance = balance + 100 where user = ?";
        JDBCUtils.updateQuery(sql1,"BB");

        System.out.println("转账成功");

    }
}
