package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 26.09.12
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsResponse {
    private String reportName;
    private Date dateOfDownload;
    private String userDisplayName;
    private String success;
    private Long requestSize;
    private Long responseSize;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getDateOfDownload() {
        return dateOfDownload;
    }

    public void setDateOfDownload(Date dateOfDownload) {
        this.dateOfDownload = dateOfDownload;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Long getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(Long requestSize) {
        this.requestSize = requestSize;
    }

    public Long getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(Long responseSize) {
        this.responseSize = responseSize;
    }
}
