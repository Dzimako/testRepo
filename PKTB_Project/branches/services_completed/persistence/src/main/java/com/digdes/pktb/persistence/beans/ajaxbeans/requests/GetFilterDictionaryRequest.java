package com.digdes.pktb.persistence.beans.ajaxbeans.requests;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 26.09.12
 * Time: 16:25
 */
public class GetFilterDictionaryRequest {
    private String filterUID;
    private List<Pair> params;
    private UserBean userBean;

    public GetFilterDictionaryRequest() {
    }

    public String getFilterUID() {
        return filterUID;
    }

    public void setFilterUID(String filterUID) {
        this.filterUID = filterUID;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public List<Pair> getParams() {
        return params;
    }

    public void setParams(List<Pair> params) {
        this.params = params;
    }
}
