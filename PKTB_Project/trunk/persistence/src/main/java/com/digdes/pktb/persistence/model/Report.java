package com.digdes.pktb.persistence.model;

import java.util.Set;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:41
 */
public interface Report {

    Long getId();

    void setId(Long id);

    Report getParentReport();

    void setParentReport(Report parentReport);

    String getUid();

    void setUid(String uid);

    String getName();

    void setName(String name);

    String getInputTemplatePath();

    void setInputTemplatePath(String inputTemplatePath);

    String getOutputTemplatePath();

    void setOutputTemplatePath(String outputTemplatePath);

    Boolean getFolder();

    void setFolder(Boolean folder);

    Set<ReportLevel> getReportLevels();

    void setReportLevels(Set<ReportLevel> reportLevels);

    Set<ReportRights> getReportRights();

    void setReportRights(Set<ReportRights> reportRights);

    Set<UserGroup> getGroupAccess();

    void setGroupAccess(Set<UserGroup> groupAccess);

    Set<ReportStat> getReportStats();

    void setReportStats(Set<ReportStat> reportStats);

    String getTreePath();

    void setTreePath(String treePath);

    Long getOrderNum();

    void setOrderNum(Long orderNum);
}
