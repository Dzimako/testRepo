package com.digdes.pktb.persistence.beans.ajaxbeans.requests;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 03.10.12
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class GetByParentReportIdRequest {
    private Long id;
    private UserBean userBean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
