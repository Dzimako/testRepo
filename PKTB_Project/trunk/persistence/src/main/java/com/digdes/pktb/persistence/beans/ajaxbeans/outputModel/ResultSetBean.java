package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;
import com.digdes.pktb.persistence.util.ProcedureResultSet;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 29.11.12
 * Time: 11:33
 */
public class ResultSetBean {
    private String key;
    private String datasource;
    private String proc_name;
    private List<Pair> params;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getProc_name() {
        return proc_name;
    }

    public void setProc_name(String proc_name) {
        this.proc_name = proc_name;
    }

    public List<Pair> getParams() {
        return params;
    }

    public void setParams(List<Pair> params) {
        this.params = params;
    }
}
