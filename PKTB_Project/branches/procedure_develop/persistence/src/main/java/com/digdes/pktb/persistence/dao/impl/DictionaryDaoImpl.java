package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.dao.DictionaryDao;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 21.11.12
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryDaoImpl implements DictionaryDao {
    private static Logger logger = Logger.getLogger("debug-log-file");

    public Map<String, String> executeDictionaryRequest(String datasource, String keyCell, String valueCell, String tableName) {
//        logger.debug("Enter executeDictionaryRequest()");
        Connection connection = null;
        ResultSet resultSet = null;
        Map<String, String> dictionaryMap = new HashMap<String, String>();
//        logger.debug("datasource: "+datasource);
//        logger.debug("keyCell: "+keyCell);
//        logger.debug("valueCell: "+valueCell);
//        logger.debug("tableName: "+tableName);
        if (datasource.trim().equals("") || keyCell.trim().equals("") || valueCell.trim().equals("") || tableName.trim().equals("")){
//            logger.debug("Exit executeDictionaryRequest()");
            return dictionaryMap;
        }
        try {
            connection = DBConnectionDispatcher.getConnection(datasource);
//            logger.debug("Database driver name: " + connection.getMetaData().getDriverName());
//            logger.debug("SQL request: " + getStatmentRequest(tableName, keyCell, valueCell));
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
//            PreparedStatement preparedStatement = connection.prepareStatement(getStatmentRequest(tableName, keyCell, valueCell),ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            resultSet = statement.executeQuery(getStatmentRequest(keyCell, valueCell, tableName));
            while ( resultSet.next() ){
                dictionaryMap.put( resultSet.getString(1).trim(), resultSet.getString(2).trim() );
            }
            resultSet.close();
        } catch (Throwable e) {
            logger.error("Error executeDictionaryRequest(): ",e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.debug("Error while closing resultSet: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.debug("Error while closing connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
//        logger.debug("Exit executeDictionaryRequest()");
        return dictionaryMap;
    }

    private String getStatmentRequest(String keyCell, String valueCell, String tableName){
        StringBuilder sqlRequest = new StringBuilder("SELECT ");
        sqlRequest.append(keyCell).append(",").append(valueCell).append(" FROM ").append("ASP2QC.").append(tableName).append(";");
        return sqlRequest.toString();
    }
}
