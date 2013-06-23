package com.digdes.pktb.persistence.dao;

import com.digdes.pktb.persistence.model.ReportStat;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 25.09.12
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public interface ReportStatDao {
    public void saveReportStat(ReportStat reportStat);

    public List<ReportStat> findWithParams(String reportName,
            Date dateOfDownloadBegin,
            Date dateOfDownloadEnd,
            String userDisplayName,
            Boolean success);
}
