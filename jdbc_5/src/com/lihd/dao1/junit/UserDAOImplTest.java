package com.lihd.dao1.junit;

import com.lihd.bean.User;
import com.lihd.dao1.BaseDAO;
import com.lihd.dao1.UserDAO;
import com.lihd.dao1.impl.UserDAOImpl;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class UserDAOImplTest {
    UserDAO dao = new UserDAOImpl();
    @Test
    public void insertUser() {
    }

    @Test
    public void deleteUserByUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void queryOneByUser() {
    }

    @Test
    public void queryAllAsList()throws Exception {
        Connection connection = BaseDAO.getConnection();
        List<User> users = dao.queryAllAsList(connection);
        users.forEach(System.out::println);
    }

    @Test
    public void getCount() {
    }

    @Test
    public void getMaxBalance() {
    }
}