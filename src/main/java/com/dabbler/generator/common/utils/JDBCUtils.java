package com.dabbler.generator.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author redDabbler
 * @create 2019-01-28 11:28
 **/
public class JDBCUtils {
    private JDBCUtils(){
        throw new UnsupportedOperationException();
    }

    /**
     * for example:
     * driverClass:com.mysql.jdbc.Driver
     * url:"jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
     *
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @return
     */
    public static Connection getConnect(String driverClass,String url,String userName,String password){
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
