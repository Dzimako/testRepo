package com.digdes.pktb.persistence.dao.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.Hashtable;

/**
 * User: Kozlov.D
 * Date: 15.10.12
 * Time: 16:03
 */
public class DBConnectionDispatcher {

    public DBConnectionDispatcher() {

    }

    public static Connection getConnection(String db_host, String db_login, String db_password) {
        Connection connection = null;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection(db_host, db_login, db_password);
        } catch (Throwable e) {
            System.out.println("Error while connecting to DB: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(String datasource) {
        Connection connection = null;
        try {

            Class.forName("com.ibm.db2.jcc.DB2Driver");
//                Class.forName("com.ibm.db2os390.sqlj.jdbc.DB2SQLJDriver");
//                connection = DriverManager.getConnection("jdbc:db2://10.32.0.249:8000/RUVC01DB2A2KT", "WPCODUSR", "WPCODUSR");
            //datasource = datasource.replaceAll("jdbc/", "");
//            Hashtable hashtable = new Hashtable();
//                        hashtable.put(Context.INITIAL_CONTEXT_FACTORY,
//                                "com.ibm.websphere.naming.WsnInitialContextFactory");
            DataSource dataSource = (DataSource) new InitialContext().lookup(datasource); //"java:comp/env/" +
            connection = dataSource.getConnection();
        } catch (Throwable e) {
            System.out.println("Error while connecting to DB: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}
