package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 22.11.12
 * Time: 11:14
 */
public class ProcedureResultBean {
    private List<String> outParams;
    private List<List<String>> resultSet;

    public List<String> getOutParams() {
        return outParams;
    }

    public void setOutParams(List<String> outParams) {
        this.outParams = outParams;
    }

    public List<List<String>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<List<String>> resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("ProcedureResultBean");
        sb.append("{outParams=").append(outParams);
        sb.append(", resultSet=").append(resultSet);
        sb.append('}');
        return sb.toString();
    }
}
