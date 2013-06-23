package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.*;

import java.util.List;
import java.util.Set;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:40
 */
public class UserImpl implements User{

    private Long id;
    private Railway railway;
    private String cn;
    private String displayName;
    private UserRole userRole;
    private String email;
    private Set<UserGroup> userGroupSet;
    private List<Report> favourites;
    private Set<ReportRights> reportRights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Railway getRailway() {
        return railway;
    }

    public void setRailway(Railway railway) {
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserGroup> getUserGroupSet() {
        return userGroupSet;
    }

    public void setUserGroupSet(Set<UserGroup> userGroupSet) {
        this.userGroupSet = userGroupSet;
    }

    public void setFavourites(List<Report> reports) {
        favourites = reports;
    }

    public List<Report> getFavourites() {
        return favourites;
    }

    public Set<ReportRights> getReportRights() {
        return reportRights;
    }

    public void setReportRights(Set<ReportRights> reportRights) {
        this.reportRights = reportRights;
    }
}
