package com.lihd.dao.impl;

import com.lihd.bean.Customer;
import com.lihd.dao.BaseDAO;
import com.lihd.dao.CustomerDAO;
import com.lihd.utils.DAOUtils;
import com.lihd.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 23:40
 */
public class CustomerImpl extends BaseDAO<Customer> implements CustomerDAO {

    @Override
    public void insert(Connection conn, Customer customer) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        JDBCUtils.updateQuery(conn,sql,customer.getName(),customer.getEmail(),customer.getBirth());
    }

    @Override
    public void updateById(Connection conn, Customer customer) {

    }

    @Override
    public void deleteById(Connection conn, Customer customer) {

    }

    @Override
    public List<Customer> queryAll(Connection conn) {
        return null;
    }

    @Override
    public Customer queryOne(Connection conn) {
        return null;
    }

    @Override
    public long getCount(Connection conn) {
        return 0;
    }

    @Override
    public Date getMaxDate(Connection conn) {
        return null;
    }

    @Override
    public String getEmailById(Connection conn, int id) {
        return null;
    }

    @Override
    public void changeNameById(Connection conn, int id, String name) {

    }
}
