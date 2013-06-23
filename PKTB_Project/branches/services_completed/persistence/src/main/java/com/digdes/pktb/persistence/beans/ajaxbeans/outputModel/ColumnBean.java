package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * User: Kozlov.D
 * Date: 02.11.12
 * Time: 14:25
 */
public class ColumnBean {
    private OperatorBean operator;

    public ColumnBean() {
    }

    public ColumnBean(OperatorBean operator) {
        this.operator = operator;
    }

    public OperatorBean getOperator() {
        return operator;
    }

    public void setOperator(OperatorBean operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ColumnBean");
        sb.append("{operator=").append(operator);
        sb.append('}');
        return sb.toString();
    }
}
