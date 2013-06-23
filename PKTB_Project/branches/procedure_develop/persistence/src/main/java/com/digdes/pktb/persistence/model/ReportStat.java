package com.digdes.pktb.persistence.model;

import java.util.Date;

/**
 * User: Kozlov.D
 * Date: 24.09.12
 * Time: 13:58
 */
public interface ReportStat {

    Long getId();

    void setId(Long id);

    Report getReport();

    void setReport(Report report);

    Date getDateOfDownload();

    void setDateOfDownload(Date dateOfDownload);

    User getUser();

    void setUser(User user);

    public Boolean getSuccess();

    public void setSuccess(Boolean success);

    public Long getRequestSize();

    public void setRequestSize(Long requestSize);

    public Long getResponseSize();

    public void setResponseSize(Long responseSize);
}
