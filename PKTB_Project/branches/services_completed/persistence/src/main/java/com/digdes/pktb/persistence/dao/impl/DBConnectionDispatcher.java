package com.digdes.pktb.persistence.dao.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 * User: Kozlov.D
 * Date: 15.10.12
 * Time: 16:03
 */
public class DBConnectionDispatcher {

    public DBConnectionDispatcher() {

        }

    public static Connection getConnection(String db_host, String db_login, String db_password){
        Connection connection = null;
        try{
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection(db_host, db_login, db_password);
        } catch (Throwable e){
            System.out.println("Error while connecting to DB: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(String datasource){
            Connection connection = null;
            try{

                Class.forName("com.ibm.db2.jcc.DB2Driver");
//                connection = DriverManager.getConnection(db_host, db_login, db_password);
                DataSource dataSource = (DataSource)new InitialContext().lookup(datasource);
                connection = dataSource.getConnection();
            } catch (Throwable e){
                System.out.println("Error while connecting to DB: " + e.getMessage());
                e.printStackTrace();
            }
            return connection;
        }
}
