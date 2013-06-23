package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 21.11.12
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryBean {
    private String dataSource;
    private String filterDBkey;
    private String filterDBkeyValue;
    private String databaseTable;
    private String dictionaryKey;
    private List<Exception> exceptions;

    public DictionaryBean() {
    }

    public DictionaryBean(String dictionaryKey) {
        this.dictionaryKey = dictionaryKey;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getFilterDBkey() {
        return filterDBkey;
    }

    public void setFilterDBkey(String filterDBkey) {
        this.filterDBkey = filterDBkey;
    }

    public String getFilterDBkeyValue() {
        return filterDBkeyValue;
    }

    public void setFilterDBkeyValue(String filterDBkeyValue) {
        this.filterDBkeyValue = filterDBkeyValue;
    }

    public String getDatabaseTable() {
        return databaseTable;
    }

    public void setDatabaseTable(String databaseTable) {
        this.databaseTable = databaseTable;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public String getDictionaryKey() {
        return dictionaryKey;
    }

    public void setDictionaryKey(String dictionaryKey) {
        this.dictionaryKey = dictionaryKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        DictionaryBean that = (DictionaryBean) o;

        return dictionaryKey.equals(that.dictionaryKey);

    }

    @Override
    public int hashCode() {
        return dictionaryKey.hashCode();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("DictionaryBean");
        sb.append("{dataSource='").append(dataSource).append('\'');
        sb.append(", filterDBkey='").append(filterDBkey).append('\'');
        sb.append(", filterDBkeyValue='").append(filterDBkeyValue).append('\'');
        sb.append(", databaseTable='").append(databaseTable).append('\'');
        sb.append(", dictionaryKey='").append(dictionaryKey).append('\'');
        sb.append(", exceptions=").append(exceptions);
        sb.append('}');
        return sb.toString();
    }

    public static class DictionaryBeanComparator implements Comparator<DictionaryBean> {
            @Override
            public int compare(DictionaryBean o1, DictionaryBean o2) {
//                logger.debug("compare "+o1.getDictionaryKey().trim()+" to "+o2.getDictionaryKey().trim());
//                logger.debug("compare result"+o1.getDictionaryKey().trim().compareTo(o2.getDictionaryKey().trim()));
                return o1.getDictionaryKey().trim().compareTo(o2.getDictionaryKey().trim());
            }
        }
}
