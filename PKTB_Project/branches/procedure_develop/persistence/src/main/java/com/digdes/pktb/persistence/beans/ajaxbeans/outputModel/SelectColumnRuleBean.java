package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 11.12.12
 * Time: 18:08
 */
public class SelectColumnRuleBean {
    private List<Integer> certainColumns;
    private ColumnNumFormulaBean formulaBean;

    public List<Integer> getCertainColumns() {
        return certainColumns;
    }

    public void setCertainColumns(List<Integer> certainColumns) {
        this.certainColumns = certainColumns;
    }

    public ColumnNumFormulaBean getFormulaBean() {
        return formulaBean;
    }

    public void setFormulaBean(ColumnNumFormulaBean formulaBean) {
        this.formulaBean = formulaBean;
    }
}
