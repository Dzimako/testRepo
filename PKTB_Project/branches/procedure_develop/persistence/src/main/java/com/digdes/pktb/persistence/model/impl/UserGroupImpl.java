package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.ReportRights;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.UserGroup;

import java.util.Set;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:42
 */
public class UserGroupImpl implements UserGroup {

    private Long id;
    private String name;
    private Set<User> users;
    private Set<ReportRights> reportRights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<ReportRights> getReportRights() {
        return reportRights;
    }

    public void setReportRights(Set<ReportRights> reportRights) {
        this.reportRights = reportRights;
    }
}
