package com.digdes.pktb.persistence.model;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public interface ReportRights {

    public Long getRightsId();

    public void setRightsId(Long rightsId);

    public User getUser();

    public void setUser(User user);

    public Report getReport();

    public void setReport(Report report);

    public UserGroup getUserGroup();

    public void setUserGroup(UserGroup userGroup);
}
