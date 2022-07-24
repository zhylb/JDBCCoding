package com.lihd.dao.junit;

import com.lihd.bean.Customer;
import com.lihd.dao.CustomerDAO;
import com.lihd.dao.impl.CustomerImpl;
import com.lihd.utils.DAOUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;

import static org.junit.Assert.*;

public class CustomerImplTest {
    CustomerImpl dao = new CustomerImpl();

    @Test
    public void insert() throws Exception{
        Connection conn = DAOUtils.getConnection();
        dao.insert(conn,new Customer(0,"Alan","Alan@qq.com",new Date(78978979848L)));
    }

    @Test
    public void updateById() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void queryAll() {
    }

    @Test
    public void queryOne() {
    }

    @Test
    public void getCount() {
    }

    @Test
    public void getMaxDate() {
    }

    @Test
    public void getEmailById() {
    }

    @Test
    public void changeNameById() {

    }
}