package com.lihd.dao1.impl;

import com.lihd.bean.User;
import com.lihd.dao1.BaseDAO;
import com.lihd.dao1.UserDAO;

import java.sql.Connection;
import java.util.List;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 9:22
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    @Override
    public void insertUser(Connection conn, User user) {
        String sql = "insert into user_table(user,password,balance) values(?,?,?)";
        updateQuery(conn,sql,user.getUser(),user.getPassword(),user.getBalance());
    }

    @Override
    public void deleteUserByUser(Connection conn, String username) {

    }

    @Override
    public void updateUser(Connection conn, User user) {
        String sql = "update user_table set  where user";
    }

    @Override
    public User queryOneByUser(Connection conn, String username) {
        return null;
    }


    @Override
    public List<User> queryAllAsList(Connection conn) {
        String sql = "select * from user_table";
        return queryForList(conn,sql);
    }

    @Override
    public long getCount(Connection conn) {
        return 0;
    }

    @Override
    public int getMaxBalance(Connection conn) {
        return 0;
    }
}
