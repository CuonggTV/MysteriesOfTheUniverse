package com.cuongtv.mysteriesoftheuniverse.utils;

import java.sql.*;

/**
 *
 * @author Le Thanh Long
 */
public class DatabaseUtils {
    private static final String URL = "jdbc:sqlserver://localhost:3306;encrypt=true;databaseName=BE3;trustServerCertificate=true;" ;
    private static final String USER= "sa";
    private static final String PASSWORD = "aaa";
    public static Connection getConnection() throws ClassNotFoundException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try{
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (Exception e){
            System.out.println("Connect fail: "+e);
        }
        return conn;

    }


}
