package com.digdes.pktb.persistence.beans.ajaxbeans.model;

import com.digdes.pktb.persistence.model.Railway;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class UserBean {
    private Long userId;
    private RailwayBean railway;
    private String cn;
    private String displayName;
    private UserRoleBean userRoleBean;

    public UserBean() {
    }

    public UserBean(Long userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RailwayBean getRailway() {
        return railway;
    }

    public void setRailway(RailwayBean railway) {
        this.railway = railway;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserRoleBean getUserRoleBean() {
        return userRoleBean;
    }

    public void setUserRoleBean(UserRoleBean userRoleBean) {
        this.userRoleBean = userRoleBean;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("UserBean");
        sb.append("{userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
