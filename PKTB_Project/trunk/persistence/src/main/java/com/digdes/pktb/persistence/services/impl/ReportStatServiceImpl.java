package com.digdes.pktb.persistence.services.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GlobalStatisticsRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.StatisticsResponse;
import com.digdes.pktb.persistence.dao.ReportDao;
import com.digdes.pktb.persistence.dao.ReportStatDao;
import com.digdes.pktb.persistence.helpers.PKTBHelper;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.ReportStat;
import com.digdes.pktb.persistence.services.ReportStatService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 26.09.12
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class ReportStatServiceImpl implements ReportStatService {
    private ReportDao reportDao;
    private ReportStatDao reportStatDao;

    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public void setReportStatDao(ReportStatDao reportStatDao) {
        this.reportStatDao = reportStatDao;
    }

    public List<StatisticsResponse> getReportStatByReportId(Long id){
        List<StatisticsResponse> reportStatisticsBean = new ArrayList<StatisticsResponse>();
        Report report = reportDao.load(id);
        for (ReportStat reportStat: report.getReportStats()){
            StatisticsResponse statisticsResponse = new StatisticsResponse();
            statisticsResponse.setDateOfDownload(reportStat.getDateOfDownload());
            statisticsResponse.setReportName(reportStat.getReport().getName());
            statisticsResponse.setRequestSize(reportStat.getRequestSize());
            statisticsResponse.setResponseSize(reportStat.getResponseSize());
            statisticsResponse.setSuccess((reportStat.getSuccess()) ? PKTBHelper.getMessage("statisticsResponse.success.true") : PKTBHelper.getMessage("statisticsResponse.success.false"));
            statisticsResponse.setUserDisplayName(reportStat.getUser().getDisplayName());
            reportStatisticsBean.add(statisticsResponse);
        }
        return reportStatisticsBean;
    }

    public List<StatisticsResponse> getGlobalReportStat(GlobalStatisticsRequest globalStatisticsRequest){
        if (globalStatisticsRequest == null)
            return null;
        List<StatisticsResponse> statisticsResponses = new ArrayList<StatisticsResponse>();
        List<ReportStat> reportStats = reportStatDao.findWithParams(
                globalStatisticsRequest.getReportName(),
                globalStatisticsRequest.getDateOfDownloadBegin(),
                globalStatisticsRequest.getDateOfDownloadEnd(),
                globalStatisticsRequest.getUserDisplayName(),
                globalStatisticsRequest.getSuccess());
        for(ReportStat reportStat: reportStats){
            StatisticsResponse statisticsResponse = new StatisticsResponse();
            statisticsResponse.setDateOfDownload(reportStat.getDateOfDownload());
            if (reportStat.getReport() != null)
                statisticsResponse.setReportName(reportStat.getReport().getName());
            statisticsResponse.setSuccess((reportStat.getSuccess()) ? PKTBHelper.getMessage("statisticsResponse.success.true") : PKTBHelper.getMessage("statisticsResponse.success.false"));
            statisticsResponse.setUserDisplayName(reportStat.getUser().getDisplayName());
            statisticsResponses.add(statisticsResponse);
        }
        System.out.println("statisticsResponses.size():"+statisticsResponses.size());
        return statisticsResponses;
    }
}
