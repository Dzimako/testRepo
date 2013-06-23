package com.digdes.pktb.persistence.model;

import java.util.List;
import java.util.Set;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:40
 */
public interface User {

    Long getId();

    void setId(Long id);

    Railway getRailway();

    void setRailway(Railway railway);

    String getCn();

    void setCn(String cn);

    String getDisplayName();

    void setDisplayName(String displayName);

    UserRole getUserRole() ;

    void setUserRole(UserRole userRole);

    String getEmail();

    void setEmail(String email);

    Set<UserGroup> getUserGroupSet();

    void setUserGroupSet(Set<UserGroup> userGroupSet);

    void setFavourites(List<Report> reports);

    List<Report> getFavourites();

    Set<ReportRights> getReportRights();

    void setReportRights(Set<ReportRights> reportRights);
}
