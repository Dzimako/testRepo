package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserGroupBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 03.10.12
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class UsersAndGroupsResponse {
    private List<UserBean> userBeans;
    private List<UserGroupBean> userGroupBeans;

    public UsersAndGroupsResponse() {
    }

    public UsersAndGroupsResponse(List<UserBean> userBeans, List<UserGroupBean> userGroupBeans) {
        this.userBeans = userBeans;
        this.userGroupBeans = userGroupBeans;
    }

    public List<UserBean> getUserBeans() {
        return userBeans;
    }

    public void setUserBeans(List<UserBean> userBeans) {
        this.userBeans = userBeans;
    }

    public List<UserGroupBean> getUserGroupBeans() {
        return userGroupBeans;
    }

    public void setUserGroupBeans(List<UserGroupBean> userGroupBeans) {
        this.userGroupBeans = userGroupBeans;
    }
}
