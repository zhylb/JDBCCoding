package com.lihd.blob;

import com.lihd.bean.Customers;
import com.lihd.utils.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 10:24
 */
public class CustomerBlob {






    /**
     * Test02 : 写出
     *
     *
     *
     */
    @Test
    public void test02 ()throws Exception{
        String sql = "select  id,name,email,birth,photo from customers where id= ?";
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1,4);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            Date birth = rs.getDate("birth");
            Blob photo = rs.getBlob("photo");

            Customers customers = new Customers(id, name, email, birth, photo);

            Blob blob = customers.getPhoto();
            InputStream is = blob.getBinaryStream();
            FileOutputStream fos = new FileOutputStream("src/com/lihd/blob/download.jpg");

            int len;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer)) != -1){
                fos.write(buffer,0,len);
            }

            System.out.println(customers);
        }

    }



    /**
     * Test01 : 写入
     *
     */
    @Test
    public void test01 () throws FileNotFoundException {
        String sql = "insert into customers(name,email,birth,photo) " +
                " values(?,?,?,?)";
        String name = "lihd";
        String email = "lihd@gmail.com";
        String birth = "2001-05-22";
        File file = new File("src/com/lihd/blob/upload.jpg");
        JDBCUtils.updateQuery(sql,name,email,birth,new FileInputStream(file));
    }


    /**
     * Test00 : 流 和 blob的转换
     * 使用 setBlob(int,InputStream) 设置Blob
     * 或者也可以使用 setObject(int,InputStream) 设置Blob
     *
     */
    @Test
    public void test00 ()throws Exception{
        Connection connection = JDBCUtils.getConnection();
        String sql = "update customers set photo = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        FileInputStream is = new FileInputStream("src/com/lihd/blob/upload.jpg");

        ps.setBlob(1,is);
        ps.setInt(2,4);

        ps.executeUpdate();

    }
}
