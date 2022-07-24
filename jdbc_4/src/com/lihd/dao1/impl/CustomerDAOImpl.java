package com.lihd.dao1.impl;

import com.lihd.bean.Customer;
import com.lihd.dao1.BaseDAO;
import com.lihd.dao1.CustomerDAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/31 9:46
 */
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {

    @Override
    public void insertCustomer(Connection conn, Customer customer) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        updateQuery(conn,sql,customer.getName(),customer.getEmail(),customer.getBirth());
    }

    @Override
    public void deleteCustomerById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        updateQuery(conn,sql,id);
    }

    @Override
    public void updateCustomer(Connection conn, Customer customer) {
        String sql = "update customers set name = ?, email = ?, birth = ? where id = ?";
        updateQuery(conn, sql, customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    @Override
    public void updateCustomerNameById(Connection conn, int id, String name) {
        String sql = "update customers set name = ? where id = ?";
        updateQuery(conn,sql,name,id);
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "select id,name,email,birth from customers where id = ?";
        return queryForOne(conn, sql, id);
    }

    @Override
    public List<Customer> getCustomers(Connection conn) {
        String sql = "select id,name,email,birth from customers";
        return queryForList(conn,sql);
    }

    @Override
    public long getCount(Connection conn) {
        String sql = "select count(*) from customers";
        return valueQuery(conn,sql);
    }

    @Override
    public Date getMaxDate(Connection conn) {
        String sql = "select max(birth) from customers";
        return valueQuery(conn,sql);
    }
}
