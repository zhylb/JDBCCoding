package com.lihd.dao;

import java.sql.Connection;
import java.util.List;

/**
 *
 *
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2022/3/30 23:10
 */
public abstract class BaseDAO<T> {

    public abstract void insert(Connection conn, T t);

    public abstract void updateById(Connection conn, T t);

    public abstract void deleteById(Connection conn, T t);

    public abstract List<T> queryAll(Connection conn);

    public abstract T queryOne(Connection conn);

    public abstract long getCount(Connection conn);






}
