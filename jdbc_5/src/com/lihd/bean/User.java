package com.lihd.bean;

/**
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 16:08
 */
public class User {
    private String user;
    private String password;
    private int balance;

    public User(String user, String password, int balance) {
        this.user = user;
        this.password = password;
        this.balance = balance;
    }

    public User() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
