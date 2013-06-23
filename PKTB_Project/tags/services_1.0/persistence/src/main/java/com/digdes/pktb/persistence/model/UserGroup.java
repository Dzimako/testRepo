package com.digdes.pktb.persistence.model;

import java.util.Set;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:41
 */
public interface UserGroup {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Set<User> getUsers();

    void setUsers(Set<User> users);

    Set<ReportRights> getReportRights();

    void setReportRights(Set<ReportRights> reportRights);
}
