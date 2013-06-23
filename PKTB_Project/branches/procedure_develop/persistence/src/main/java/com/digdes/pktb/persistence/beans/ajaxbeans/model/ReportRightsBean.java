package com.digdes.pktb.persistence.beans.ajaxbeans.model;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class ReportRightsBean {
    private Long rightsId;
    private UserBean user;
    private ReportBean report;
    private UserGroupBean userGroup;
    private Boolean isExist;

    public Long getRightsId() {
        return rightsId;
    }

    public void setRightsId(Long rightsId) {
        this.rightsId = rightsId;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public ReportBean getReport() {
        return report;
    }

    public void setReport(ReportBean report) {
        this.report = report;
    }

    public UserGroupBean getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupBean userGroup) {
        this.userGroup = userGroup;
    }

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }
}
