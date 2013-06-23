package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Set;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:41
 */
public class ReportImpl implements Report {

    private Long id;
    private Report parentReport;
    private String uid;
    private String name;
    private String inputTemplatePath;
    private String outputTemplatePath;
    private Boolean folder;
    private Set<ReportLevel> reportLevels;
    private Set<ReportStat> reportStats;
    private String treePath;
    private Long orderNum;
    private Boolean showInTree;
    private String fullName;

    private Set<ReportRights> reportRights;
    private Set<UserGroup> groupAccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report getParentReport() {
        return parentReport;
    }

    public void setParentReport(Report parentReport) {
        this.parentReport = parentReport;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputTemplatePath() {
        return inputTemplatePath;
    }

    public void setInputTemplatePath(String inputTemplatePath) {
        this.inputTemplatePath = inputTemplatePath;
    }

    public String getOutputTemplatePath() {
        return outputTemplatePath;
    }

    public void setOutputTemplatePath(String outputTemplatePath) {
        this.outputTemplatePath = outputTemplatePath;
    }

    public Boolean getFolder() {
        return folder;
    }

    public void setFolder(Boolean folder) {
        this.folder = folder;
    }

    public Set<ReportLevel> getReportLevels() {
        return reportLevels;
    }

    public void setReportLevels(Set<ReportLevel> reportLevels) {
        this.reportLevels = reportLevels;
    }

    public Set<ReportRights> getReportRights() {
        return reportRights;
    }

    public void setReportRights(Set<ReportRights> reportRights) {
        this.reportRights = reportRights;
    }

    public Set<UserGroup> getGroupAccess() {
        return groupAccess;
    }

    public void setGroupAccess(Set<UserGroup> groupAccess) {
        this.groupAccess = groupAccess;
    }

    public Set<ReportStat> getReportStats() {
        return reportStats;
    }

    public void setReportStats(Set<ReportStat> reportStats) {
        this.reportStats = reportStats;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public static class ReportComparator implements Comparator<Report> {

        public int compare(Report o1, Report o2) {
            if (o1.getName() == null || o2.getName() == null)
                return 0;
            try {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.parse(o1.getName().trim().split(" ")[0]);
                numberFormat.parse(o2.getName().trim().split(" ")[0]);

                String [] num1 = o1.getName().trim().split(" ")[0].split("\\.");
                String [] num2 = o2.getName().trim().split(" ")[0].split("\\.");

                for (int i = 0; i < Math.min(num1.length, num2.length); i++) {
                    if (numberFormat.parse(num1[i]).intValue() > numberFormat.parse(num2[i]).intValue())
                        return 1;
                    else if (numberFormat.parse(num1[i]).intValue() < numberFormat.parse(num2[i]).intValue())
                        return -1;
                }

                return 0;
            } catch (ParseException e) {
                System.out.println(o1.getName());
                System.out.println(o2.getName());
                return o1.getName().compareTo(o2.getName());
            }

        }
    }

    public static class ReportOrderNumComparator implements Comparator<Report> {

        public int compare(Report o1, Report o2) {
            if (o1.getOrderNum() == null || o2.getOrderNum() == null)
                return 0;
            return o1.getOrderNum().compareTo(o2.getOrderNum());
        }
    }

    public Boolean getShowInTree() {
        return showInTree;
    }

    public void setShowInTree(Boolean showInTree) {
        this.showInTree = showInTree;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
