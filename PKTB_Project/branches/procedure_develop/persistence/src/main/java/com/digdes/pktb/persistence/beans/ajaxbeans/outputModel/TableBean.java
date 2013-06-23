package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 02.11.12
 * Time: 14:24
 */
public class TableBean {
    private List<ColumnBean> columns;
    private List<RowFunctionBean> rowFunctions;
    private ResultSetBean inputResultSet;
    private ResultSetBean outputResultSet;

    public TableBean() {
    }

    public List<ColumnBean> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnBean> columns) {
        this.columns = columns;
    }

    public List<RowFunctionBean> getRowFunctions() {
        return rowFunctions;
    }

    public void setRowFunctions(List<RowFunctionBean> rowFunctions) {
        this.rowFunctions = rowFunctions;
    }

    public ResultSetBean getInputResultSet() {
        return inputResultSet;
    }

    public void setInputResultSet(ResultSetBean inputResultSet) {
        this.inputResultSet = inputResultSet;
    }

    public ResultSetBean getOutputResultSet() {
        return outputResultSet;
    }

    public void setOutputResultSet(ResultSetBean outputResultSet) {
        this.outputResultSet = outputResultSet;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TableBean");
        sb.append("{columns=").append(columns);
        sb.append(", rowFunctions=").append(rowFunctions);
        sb.append('}');
        return sb.toString();
    }
}
