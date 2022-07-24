package com.lihd.dao;

import java.sql.Connection;
import java.sql.Date;

public interface CustomerDAO {

    public Date getMaxDate(Connection conn);

    public String getEmailById(Connection conn, int id);

    public void changeNameById(Connection conn, int id,String name);
}
