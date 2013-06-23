package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.ReportStat;
import com.digdes.pktb.persistence.model.User;

import java.util.Date;

/**
 * User: Kozlov.D
 * Date: 24.09.12
 * Time: 13:55
 */
public class ReportStatImpl implements ReportStat {

    private Long id;
    private Report report;
    private Date dateOfDownload;
    private User user;
    private Boolean success;
    private Long requestSize;
    private Long responseSize;

    public ReportStatImpl() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Date getDateOfDownload() {
        return dateOfDownload;
    }

    public void setDateOfDownload(Date dateOfDownload) {
        this.dateOfDownload = dateOfDownload;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
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
