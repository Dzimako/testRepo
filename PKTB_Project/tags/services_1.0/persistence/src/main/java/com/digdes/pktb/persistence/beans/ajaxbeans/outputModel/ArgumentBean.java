package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * User: Kozlov.D
 * Date: 02.11.12
 * Time: 14:27
 */
public class ArgumentBean {
    private String defaultValue;
    private OperatorBean operator;
    private Integer columnNumber;

    public ArgumentBean() {
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public OperatorBean getOperator() {
        return operator;
    }

    public void setOperator(OperatorBean operator) {
        this.operator = operator;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ArgumentBean");
        sb.append("{defaultValue='").append(defaultValue).append('\'');
        sb.append(", operator=").append(operator);
        sb.append(", columnNumber=").append(columnNumber);
        sb.append('}');
        return sb.toString();
    }
}
