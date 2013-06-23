package com.digdes.pktb.persistence.beans.ajaxbeans.requests;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 01.10.12
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class GlobalStatisticsRequest {
    private String reportName;
    private Date dateOfDownloadBegin;
    private Date dateOfDownloadEnd;
    private String userDisplayName;
    private Boolean success;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getDateOfDownloadBegin() {
        return dateOfDownloadBegin;
    }

    public void setDateOfDownloadBegin(Date dateOfDownloadBegin) {
        this.dateOfDownloadBegin = dateOfDownloadBegin;
    }

    public Date getDateOfDownloadEnd() {
        return dateOfDownloadEnd;
    }

    public void setDateOfDownloadEnd(Date dateOfDownloadEnd) {
        this.dateOfDownloadEnd = dateOfDownloadEnd;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
