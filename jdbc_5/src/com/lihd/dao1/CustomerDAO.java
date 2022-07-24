package com.lihd.dao1;

import com.lihd.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface CustomerDAO {
    void insertCustomer(Connection conn, Customer customer);
    void deleteCustomerById(Connection conn, int id);
    void updateCustomer(Connection conn, Customer customer);
    void updateCustomerNameById(Connection conn,int id,String name);

    Customer getCustomerById(Connection conn ,int id);
    List<Customer> getCustomers(Connection conn);

    long getCount(Connection conn);
    Date getMaxDate(Connection conn);
}
