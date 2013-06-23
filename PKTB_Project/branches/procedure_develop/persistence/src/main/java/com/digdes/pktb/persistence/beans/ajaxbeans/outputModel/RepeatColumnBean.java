package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * User: Kozlov.D
 * Date: 13.12.12
 * Time: 11:44
 */
public class RepeatColumnBean {
    private SelectColumnRuleBean columnIndexesRule;
    private ColumnBean column;

    public SelectColumnRuleBean getColumnIndexesRule() {
        return columnIndexesRule;
    }

    public void setColumnIndexesRule(SelectColumnRuleBean columnIndexesRule) {
        this.columnIndexesRule = columnIndexesRule;
    }

    public ColumnBean getColumn() {
        return column;
    }

    public void setColumn(ColumnBean column) {
        this.column = column;
    }
}
