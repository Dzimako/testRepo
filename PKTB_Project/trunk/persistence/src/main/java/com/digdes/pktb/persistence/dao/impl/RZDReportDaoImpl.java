package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.outputModel.*;
import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;
import com.digdes.pktb.persistence.beans.xml.RowXML;
import com.digdes.pktb.persistence.dao.RZDReportDao;
import com.digdes.pktb.persistence.helpers.PKTBHelper;
import com.digdes.pktb.persistence.util.ProcedureResultSet;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Kozlov.D
 * Date: 16.10.12
 * Time: 14:29
 */
public class RZDReportDaoImpl implements RZDReportDao{

    private static Logger logger = Logger.getLogger("debug-log-file");

    public RZDReportDaoImpl() {
    }

    public ProcedureResultSet callProcedure(String datasource, String procName, List<Pair> parameters) {
        Connection connection = null;
        try {
            connection = DBConnectionDispatcher.getConnection(datasource);
            CallableStatement statement = connection.prepareCall(getProcedureRequestString(procName, parameters), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            List<Pair> outParams = setParameters(statement, parameters);
            ProcedureResultSet procedureResultSet = new ProcedureResultSet();
            statement.execute();
            String error = "0";
            String errorCode = "";
            logger.debug("outParams.toString(): " + outParams.toString());
            if (outParams != null) {
                List<String> outParamValues = new ArrayList<String>();
                Integer outParamNum = 0;
                for (Pair outParam : outParams) {
                    outParamValues.add(getParameter(statement, outParam));
                    if ((outParam.getMarkerSQLCode() != null && outParam.getMarkerSQLCode()) || outParamNum == 0) {
                        error = getParameter(statement, outParam);
                    } else if (outParamNum == 1) {
                        errorCode = getParameter(statement, outParam);
                    }
                    outParamNum++;
                }
                procedureResultSet.setOutParamValues(outParamValues);
            }

            logger.debug("Error: " + error + "; Error code: " + errorCode);
            try {
                if (Integer.parseInt(error) == 0) {
                    ResultSet resultSet = statement.getResultSet();
                    procedureResultSet.setColumnNames(getColumnNames(resultSet));
                    procedureResultSet.setResultSetData(getCoveredResultSet(resultSet));
                }
            } catch (NumberFormatException e) {
                logger.error("NumberFormatException while parseInt: ", e);
                System.out.println("NumberFormatException while parseInt: " + e.getMessage());
            }
            return procedureResultSet;
        } catch (SQLException e) {
            logger.error("SQLException while calling procedure: ", e);
            System.out.println("SQLException while calling procedure: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException while calling procedure: ", e);
            System.out.println("UnsupportedEncodingException while calling procedure: " + e.getMessage());

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error while closing connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private List<String> getColumnNames(ResultSet resultSet){
        int columNumber = 1;
                List<String> values = new ArrayList<String>();
                try {
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    while (columNumber <= resultSetMetaData.getColumnCount()) {
                        values.add(resultSetMetaData.getColumnName(columNumber));
                        columNumber++;
                    }
                } catch (SQLException e) {
                    System.out.println("SQLException while getting column names: " + e.getMessage());
                }
        return values;
    }

    private List<List<String>> getCoveredResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                List<List<String>> coveredResultSet = new ArrayList<List<String>>();
                ResultSetMetaData rsMetaData = resultSet.getMetaData();
                int numColumns = rsMetaData.getColumnCount();

                while (resultSet.next()) {
                    List<String> values = new ArrayList<String>();
                    int columNumber = 1;
                    while (columNumber <= numColumns) {
                        values.add(resultSet.getString(columNumber));
                        columNumber++;
                    }
                    coveredResultSet.add(values);
                }
                return coveredResultSet;
            }
        } catch (SQLException e) {
            System.out.println("Error while covering resultSet: " + e.getMessage());
            logger.error("Error while covering resultSet: ", e);
        }
        return null;
    }

    private String getProcedureRequestString(String procName, List<Pair> parameters) {
        StringBuilder sqlRequest = new StringBuilder("CALL ASP2QC.").append(procName);
        Integer numOfParam = 0;
        if (parameters != null) {
            sqlRequest.append("(");
            while (numOfParam < parameters.size()) {
                sqlRequest.append("?");
                numOfParam++;
                if (numOfParam < parameters.size()) {
                    sqlRequest.append(",");
                }
            }
            sqlRequest.append(")");
        }
        return sqlRequest.toString();
    }

   /* private List<TableXML> getTableXMLsFromResult(ResultSet cachedRowSet, GetReportRequest getReportRequest) {
        List<TableXML> tables = new ArrayList<TableXML>();
        CellCalculator cellCalculator = new CellCalculator(cachedRowSet, getReportRequest.getDictionarys());
        System.out.println("dictionarys:");
        System.out.println(getReportRequest.getDictionarys());
        try {
            if (cachedRowSet != null) {
                ResultSetMetaData rsmd = cachedRowSet.getMetaData();
//                DatabaseMetaData.supportsResultSetType();
                int numColumns = rsmd.getColumnCount();
//                System.out.println("getTableXMLsFromResult() : numColumns = " + numColumns);
                if (getReportRequest != null && getReportRequest.getTableBeans() != null) {
                    for (TableBean tableBean : getReportRequest.getTableBeans()) {
                        TableXML tableXML = new TableXML();
                        List<RowXML> rowXMLs = new ArrayList<RowXML>();
                        Integer rowIndex = 1;
                        System.out.println("Type of current result set: " + cachedRowSet.getType() + ". Needed " + ResultSet.TYPE_SCROLL_SENSITIVE);
                        detectTypeOfResultSet(cachedRowSet.getType());
//                        cachedRowSet.beforeFirst();
                        while (cachedRowSet.next()) {
                            RowXML rowXML = new RowXML();
                            List<String> values = new ArrayList<String>();
                            if (tableBean.getColumns() != null && tableBean.getColumns().size() > 0) {
                                for (ColumnBean columnBean : tableBean.getColumns()) {
                                    String valueToAdd = cellCalculator.calculateCellValue(columnBean.getOperator(), rowIndex);
                                    if (valueToAdd == null || valueToAdd.equalsIgnoreCase("")) {
                                        valueToAdd = "-";
                                    }
                                    values.add(valueToAdd);
                                }
                            } else {
                                if(rowIndex == 1){
                                    rowXMLs.add(getHeadersRowXML(rsmd, numColumns));
                                }
                                int columNumber = 1;
                                while (columNumber <= numColumns) {
                                    values.add(cachedRowSet.getString(columNumber));
                                    columNumber++;
                                }
                            }
                            rowXML.setValues(values);
                            rowXMLs.add(rowXML);
                            rowIndex++;
                        }
                        tableXML.setRows(rowXMLs);
                        tables.add(tableXML);
                    }
                }
            }
        } catch (Throwable e) {
            System.out.println("Error while parsing resultSet: " + e.getMessage());
            logger.error("Error while parsing resultSet: ", e);
        }
        return tables;
    }*/

    private void detectTypeOfResultSet(int resultSetType) {
        switch (resultSetType) {
            case ResultSet.TYPE_SCROLL_SENSITIVE:
                logger.debug("ResultSet.TYPE_SCROLL_SENSITIVE");
                break;
            case ResultSet.TYPE_SCROLL_INSENSITIVE:
                logger.debug("ResultSet.TYPE_SCROLL_INSENSITIVE");
                break;
            case ResultSet.TYPE_FORWARD_ONLY:
                logger.debug("ResultSet.TYPE_FORWARD_ONLY");
                break;
            default:
                logger.debug("Invalid resultSet type");
        }
    }


    private List<Pair> setParameters(CallableStatement statement, List<Pair> parameters) {
        try {
            List<Pair> outParamNumbers = new ArrayList<Pair>();
            if (parameters != null && statement != null) {
                for (Pair param : parameters) {
                    if (param.getInputParam() != null && param.getInputParam().equalsIgnoreCase(PKTBHelper.PKTB_INPUT_PARAM_KEY)) {
                        setParameter(statement, param);
                        //statement.setString(param.getOrderNum(), param.getValue());
                    } else if (param.getInputParam() != null && param.getInputParam().equalsIgnoreCase(PKTBHelper.PKTB_OUTPUT_PARAM_KEY)) {
                        statement.registerOutParameter(param.getOrderNum(), param.getParamDataType());
                        outParamNumbers.add(param);
                    } else {
                        setParameter(statement, param);
                        statement.registerOutParameter(param.getOrderNum(), param.getParamDataType());
                        outParamNumbers.add(param);
                    }
                }
            }
            return outParamNumbers;
        } catch (Throwable e) {
            System.out.println("Error while setting parameters to callable statement: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void setParameter(CallableStatement callableStatement, Pair parameter) throws SQLException {
        switch (parameter.getParamDataType()) {
            case Types.SMALLINT:
                callableStatement.setShort(parameter.getOrderNum(), Short.parseShort(parameter.getValue()));
                break;
            case Types.BIGINT:
                callableStatement.setLong(parameter.getOrderNum(), Long.parseLong(parameter.getValue()));
                break;
            case Types.CHAR:
                callableStatement.setString(parameter.getOrderNum(), parameter.getValue());
                break;
            case Types.DOUBLE:
                callableStatement.setDouble(parameter.getOrderNum(), Double.parseDouble(parameter.getValue()));
                break;
            case Types.INTEGER:
                callableStatement.setInt(parameter.getOrderNum(), Integer.parseInt(parameter.getValue()));
                break;
            case Types.VARCHAR:
                callableStatement.setString(parameter.getOrderNum(), parameter.getValue());
                break;
            default:
                callableStatement.setString(parameter.getOrderNum(), parameter.getValue());
        }
    }

    private String getParameter(CallableStatement callableStatement, Pair parameter) throws SQLException, UnsupportedEncodingException {
        switch (parameter.getParamDataType()) {
            case Types.SMALLINT:
                return callableStatement.getShort(parameter.getOrderNum()) + "";
            case Types.BIGINT:
                return callableStatement.getLong(parameter.getOrderNum()) + "";
//                    case Types.CHAR:  return callableStatement.getString(parameter.getOrderNum());
            case Types.DOUBLE:
                return callableStatement.getDouble(parameter.getOrderNum()) + "";
            case Types.INTEGER:
                return callableStatement.getInt(parameter.getOrderNum()) + "";
            case Types.TIMESTAMP:
                return callableStatement.getTimestamp(parameter.getOrderNum()) + "";
//                    case Types.VARCHAR : return callableStatement.getString(parameter.getOrderNum());
            default:
//                        InputStreamReader instr = new InputStreamReader("", "windows-1251");
                String paramValue = new String(new String(callableStatement.getString(parameter.getOrderNum()).getBytes("windows-1251"), "windows-1251").getBytes("UTF-8"));

                logger.debug("String parametr: " + paramValue);
                return paramValue;
//                        System.out.println("String parameter: " + callableStatement.getString(parameter.getOrderNum()));
//                        System.out.println("String parameter UTF-8: " + URLDecoder.decode(callableStatement.getString(parameter.getOrderNum()), "UTF-8"));
//                        System.out.println("String parameter win1251: " + URLDecoder.decode(callableStatement.getString(parameter.getOrderNum()), "windows-1251"));
//                        return URLDecoder.decode(callableStatement.getString(parameter.getOrderNum()), "windows-1251");//URLEncoder.encode(callableStatement.getString(parameter.getOrderNum()), "UTF-8");
        }
    }
}
