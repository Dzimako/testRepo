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

    public RowFunctionBean() {
    }

    public OperatorBean getOperatorBean() {
        return operator;
    }

    public void setOperatorBean(OperatorBean operatorBean) {
        this.operator = operatorBean;
    }

    public List<ColumnBean> getColumnBeans() {
        return columns;
    }

    public void setColumnBeans(List<ColumnBean> columnBeans) {
        this.columns = columnBeans;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("RowFunctionBean");
        sb.append("{columnBeans=").append(columns);
        sb.append('}');
        return sb.toString();
    }
}
