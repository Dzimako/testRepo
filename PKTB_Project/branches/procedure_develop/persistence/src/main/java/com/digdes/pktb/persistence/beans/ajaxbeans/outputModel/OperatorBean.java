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
    private SelectColumnRuleBean columnRuleBean;
    private List<ArgumentBean> dynamicArguments;

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

    public SelectColumnRuleBean getColumnRuleBean() {
        return columnRuleBean;
    }

    public void setColumnRuleBean(SelectColumnRuleBean columnRuleBean) {
        this.columnRuleBean = columnRuleBean;
    }

    public List<ArgumentBean> getDynamicArguments() {
        return dynamicArguments;
    }

    public void setDynamicArguments(List<ArgumentBean> dynamicArguments) {
        this.dynamicArguments = dynamicArguments;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("OperatorBean");
        sb.append("{operatorKey='").append(operatorKey).append('\'');
        sb.append(", arguments=").append(arguments);
        sb.append(", columnRuleBean=").append(columnRuleBean);
        sb.append(", dynamicArguments=").append(dynamicArguments);
        sb.append('}');
        return sb.toString();
    }
}
