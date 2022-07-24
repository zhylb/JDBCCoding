package com.lihd.dao1;


import com.lihd.bean.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO {
    void insertUser(Connection conn, User user);
    void deleteUserByUser(Connection conn, String username);
    void updateUser(Connection conn, User user);
    User queryOneByUser(Connection conn,String username);
    List<User> queryAllAsList(Connection conn);

    long getCount(Connection conn);
    int getMaxBalance(Connection conn);


}
