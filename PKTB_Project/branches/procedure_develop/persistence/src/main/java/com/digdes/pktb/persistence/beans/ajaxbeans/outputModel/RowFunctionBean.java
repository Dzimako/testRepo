package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 02.11.12
 * Time: 14:27
 */
public class RowFunctionBean {
    private OperatorBean operator;
    private List<ColumnBean> columns;
    private List<RowFunctionBean> rowFunctions;
    private ResultSetBean resultSetToUse;
    private List<RepeatColumnBean> repeatColumns;

    public RowFunctionBean() {
    }

    public OperatorBean getOperator() {
        return operator;
    }

    public void setOperator(OperatorBean operator) {
        this.operator = operator;
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

    public ResultSetBean getResultSetToUse() {
        return resultSetToUse;
    }

    public void setResultSetToUse(ResultSetBean resultSetToUse) {
        this.resultSetToUse = resultSetToUse;
    }

    public List<RepeatColumnBean> getRepeatColumns() {
        return repeatColumns;
    }

    public void setRepeatColumns(List<RepeatColumnBean> repeatColumns) {
        this.repeatColumns = repeatColumns;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("RowFunctionBean");
        sb.append("{operator=").append(operator);
        sb.append(", columns=").append(columns);
        sb.append(", rowFunctions=").append(rowFunctions);
        sb.append(", resultSetToUse=").append(resultSetToUse);
        sb.append(", repeatColumns=").append(repeatColumns);
        sb.append('}');
        return sb.toString();
    }
}
