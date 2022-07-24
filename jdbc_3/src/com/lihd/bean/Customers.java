package com.lihd.bean;

import java.io.File;
import java.sql.Blob;
import java.sql.Date;

/**
 * setBlob/getBlob
 * 其中Blob是java中的一个 字段
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 10:21
 */
public class Customers {
    private int id;
    private String name;
    private String email;
    private Date birth;
    private Blob photo;

    public Customers() {
    }

    public Customers(int id, String name, String email, Date birth, Blob photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", photo=" + photo +
                '}';
    }
}
