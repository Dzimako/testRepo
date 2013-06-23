package com.digdes.pktb.persistence.beans.ajaxbeans.requests;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.outputModel.DictionaryBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.outputModel.TableBean;
import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 26.09.12
 * Time: 17:47
 */
public class GetReportRequest {
    private Long reportId;
    private UserBean userBean;
    private List<Pair> pairList;
    private List<TableBean> tableBeans;
    private List<DictionaryBean> dictionaries;
    private List<String> importedDictionaries;
    private String db_host;
    private String db_login;
    private String db_password;
    private String datasource;
    private String proc_name;

    public GetReportRequest() {
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public List<Pair> getPairList() {
        return pairList;
    }

    public void setPairList(List<Pair> pairList) {
        this.pairList = pairList;
    }

    public String getDb_host() {
        return db_host;
    }

    public void setDb_host(String db_host) {
        this.db_host = db_host;
    }

    public String getDb_login() {
        return db_login;
    }

    public void setDb_login(String db_login) {
        this.db_login = db_login;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getProc_name() {
        return proc_name;
    }

    public void setProc_name(String proc_name) {
        this.proc_name = proc_name;
    }

    public List<TableBean> getTableBeans() {
        return tableBeans;
    }

    public void setTableBeans(List<TableBean> tableBeans) {
        this.tableBeans = tableBeans;
    }

    public List<DictionaryBean> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<DictionaryBean> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public List<String> getImportedDictionaries() {
        return importedDictionaries;
    }

    public void setImportedDictionaries(List<String> importedDictionaries) {
        this.importedDictionaries = importedDictionaries;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GetReportRequest");
        sb.append("{reportId=").append(reportId);
        sb.append(", userBean=").append(userBean);
        sb.append(", pairList=").append(pairList);
        sb.append('}');
        return sb.toString();
    }
}
