package com.digdes.pktb.persistence.beans.ajaxbeans.requests;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;

/**
 * User: Kozlov.D
 * Date: 26.09.12
 * Time: 16:25
 */
public class GetFilterDictionaryRequest {
    private String filterUID;
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
}
