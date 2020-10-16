package com.qianfeng.meeting.business.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class QfDbUtils {
    private static QueryRunner qr = null;
    private static DataSource dataSource = null;
    private static ThreadLocal<Connection> threadLocal =new ThreadLocal<>();

    static {
        dataSource = new ComboPooledDataSource();
        qr = new QueryRunner(dataSource);
    }

    /**
     * 获取QueryRunner对象
     * @return
     */
    public static QueryRunner getQr(){
        return qr;
    }

    public static Connection getConnection() throws SQLException{
        Connection connection = threadLocal.get();
        if(connection == null){
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 开启事物
     */
    public static void beginTransaction() throws SQLException{
        Connection connection = threadLocal.get();
        if (connection != null){
            throw new SQLException("请不要重复开启事物");
        }
        connection = getConnection();
        connection.setAutoCommit(false);
    }

    /**
     * 提交事物
     * @throws SQLException
     */
    public static void commitTransaction() throws SQLException{
        Connection connection = threadLocal.get();
        if (connection == null){
            throw new SQLException("没有开启事物不能提交");
        }
        connection.commit();
        connection.close();
        threadLocal.remove();
    }

    public static void rollbackTransaction() throws SQLException{
        Connection connection = threadLocal.get();
        if (connection == null ){
            throw  new SQLException("没有开启事物不能回滚");
        }
        connection.rollback();
        connection.close();
        threadLocal.remove();
    }
}
