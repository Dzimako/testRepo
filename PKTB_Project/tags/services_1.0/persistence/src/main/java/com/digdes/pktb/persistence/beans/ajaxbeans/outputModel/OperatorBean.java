package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 02.11.12
 * Time: 14:26
 */
public class OperatorBean {
    private String operatorKey;
    private List<ArgumentBean> arguments;

    public OperatorBean() {
    }

    public String getOperatorKey() {
        return operatorKey;
    }

    public void setOperatorKey(String operatorKey) {
        this.operatorKey = operatorKey;
    }

    public List<ArgumentBean> getArguments() {
        return arguments;
    }

    public void setArguments(List<ArgumentBean> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OperatorBean");
        sb.append("{operatorKey='").append(operatorKey).append('\'');
        sb.append(", arguments=").append(arguments);
        sb.append('}');
        return sb.toString();
    }
}
