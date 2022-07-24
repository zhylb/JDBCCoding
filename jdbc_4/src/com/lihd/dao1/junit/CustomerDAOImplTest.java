package com.lihd.dao1.junit;

import com.lihd.bean.Customer;
import com.lihd.dao1.BaseDAO;
import com.lihd.dao1.CustomerDAO;
import com.lihd.dao1.impl.CustomerDAOImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerDAOImplTest {

    private CustomerDAO dao = new CustomerDAOImpl();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Date getDateByString(String  s){
        try{
            java.util.Date parse = sdf.parse(s);
            return new Date(parse.getTime());
        }catch (Exception e){

        }
        return null;
    }

    @Test
    public void insertCustomer() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            Customer customer = new Customer(1, "温迪", "无风之地", getDateByString("2001-03-30"));
            dao.insertCustomer(connection,customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }

    }

    @Test
    public void deleteCustomerById() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            dao.deleteCustomerById(connection,4);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }
    }

    @Test
    public void updateCustomer() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            Customer customer = new Customer(1, "吕布", "镇收邪祟", getDateByString("0203-03-30"));
            dao.updateCustomer(connection,customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }
    }

    @Test
    public void updateCustomerNameById() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            dao.updateCustomerNameById(connection,20,"葬花吟");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }
    }

    @Test
    public void getCustomerById() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            Customer customerById = dao.getCustomerById(connection, 7);
            System.out.println(customerById);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }
    }

    @Test
    public void getCustomers() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            List<Customer> customers = dao.getCustomers(connection);
            customers.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }
    }

    @Test
    public void getCount() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            long count = dao.getCount(connection);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }
    }

    @Test
    public void getMaxDate() {
        Connection connection = null;
        try {
            connection = BaseDAO.getConnection();
            Date maxDate = dao.getMaxDate(connection);
            System.out.println(maxDate);
            String format = sdf.format(maxDate);
            System.out.println(format);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.close(connection);
        }
    }
}