package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.ReportRights;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.UserGroup;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class ReportRightsImpl implements ReportRights {
    private Long rightsId;
    private User user;
    private Report report;
    private UserGroup userGroup;

    public ReportRightsImpl() {
    }

    public ReportRightsImpl(User user) {
        this.user = user;
    }

    public ReportRightsImpl(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public ReportRightsImpl(User user, Report report) {
        this.user = user;
        this.report = report;
    }

    public ReportRightsImpl(Report report, UserGroup userGroup) {
        this.report = report;
        this.userGroup = userGroup;
    }

    public Long getRightsId() {
        return rightsId;
    }

    public void setRightsId(Long rightsId) {
        this.rightsId = rightsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }


    public static class ReportRightsUserComparator implements Comparator<ReportRights> {

        public int compare(ReportRights o1, ReportRights o2) {
            if (o1.getUser() == null ||o2.getUser() == null)
                return 0;
            return o1.getUser().getId().compareTo(o2.getUser().getId());
        }
    }

    public static class ReportRightsGroupComparator implements Comparator<ReportRights> {

        public int compare(ReportRights o1, ReportRights o2) {
            if (o1.getUserGroup() == null ||o2.getUserGroup() == null)
                return 0;
            return o1.getUserGroup().getId().compareTo(o2.getUserGroup().getId());
        }
    }
}
