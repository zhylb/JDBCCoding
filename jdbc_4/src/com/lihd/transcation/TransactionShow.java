package com.lihd.transcation;

import com.lihd.bean.User;
import com.lihd.utils.JDBCUtils;

import java.sql.Connection;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 16:17
 */
public class TransactionShow {

    public static void main(String[] args) {
        int num = 0;
        num = Connection.TRANSACTION_READ_UNCOMMITTED;
        num = Connection.TRANSACTION_REPEATABLE_READ;


        Thread reader1 = new Thread(new Reader(null,num,1000,"刚开始 "));
        Thread writer = new Thread(new Writer(reader1,"写进程"));
        Thread reader2 = new Thread(new Reader(writer,num,3000,"一会后 "));

        reader1.start();
        writer.start();
        reader2.start();



    }

}

class Writer implements Runnable{


    Thread join;
    private String name;

    public Writer(Thread join, String name) {
        this.join = join;
        this.name = name;
    }

    public Writer(String name) {

        this.name = name;
    }



    @Override
    public void run() {
        Connection conn = null;

        try {
            if(join != null){
                join.join();
            }
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "update user_table set balance = 7000 where user = ?";
            JDBCUtils.updateQuery(conn,sql,"CC");
            System.out.println(name + " 修改了数据 ");
            conn.rollback();
            System.out.println(name + " 撤销了修改数据 ");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn,null);
        }
    }
}

class Reader implements Runnable{
    private Thread join;
    private int num;
    private int sleep;
    private String name;

    public Reader(int sleep) {
        this.sleep = sleep;
    }

    public Reader(Thread join,int num, int sleep, String name) {
        this.join = join;
        this.num = num;
        this.sleep = sleep;
        this.name = name;
    }

    @Override
    public void run() {
        Connection conn = null;
        try {
            if(join != null){
                join.join();
            }
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            conn.setTransactionIsolation(num);



            String sql = "select user,password,balance from user_table where user = ?";
            User user = JDBCUtils.queryForOne(User.class,conn, sql, "CC");
            System.out.println(name + " 读取的数据为 " +user);

            conn.commit();


            sql = "select user,password,balance from user_table where user = ?";
            user = JDBCUtils.queryForOne(User.class,conn, sql, "CC");
            System.out.println(name + "提交数据后  再次读取的数据为 " +user);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn,null);
        }

    }
}
