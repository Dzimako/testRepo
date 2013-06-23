package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * User: Kozlov.D
 * Date: 11.12.12
 * Time: 18:19
 */
public class ColumnNumFormulaBean {
    private Boolean fixedPosition;
    private Boolean singleValue;
    private Integer numToBegin;
    private Integer numToEnd;
    private String formulaString;

    public Integer getNumToBegin() {
        return numToBegin;
    }

    public void setNumToBegin(Integer numToBegin) {
        this.numToBegin = numToBegin;
    }

    public Integer getNumToEnd() {
        return numToEnd;
    }

    public void setNumToEnd(Integer numToEnd) {
        this.numToEnd = numToEnd;
    }

    public String getFormulaString() {
        return formulaString;
    }

    public void setFormulaString(String formulaString) {
        this.formulaString = formulaString;
    }

    public Boolean getFixedPosition() {
        return fixedPosition;
    }

    public void setFixedPosition(Boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
    }

    public Boolean getSingleValue() {
        return singleValue;
    }

    public void setSingleValue(Boolean singleValue) {
        this.singleValue = singleValue;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("ColumnNumFormulaBean");
        sb.append("{fixedPosition=").append(fixedPosition);
        sb.append(", singleValue=").append(singleValue);
        sb.append(", numToBegin=").append(numToBegin);
        sb.append(", numToEnd=").append(numToEnd);
        sb.append(", formulaString='").append(formulaString).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
