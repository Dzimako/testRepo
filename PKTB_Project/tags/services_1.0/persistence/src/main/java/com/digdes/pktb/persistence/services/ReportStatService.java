package com.digdes.pktb.persistence.services;

import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GlobalStatisticsRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.StatisticsResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 26.09.12
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public interface ReportStatService {
    public List<StatisticsResponse> getReportStatByReportId(Long id);

    public List<StatisticsResponse> getGlobalReportStat(GlobalStatisticsRequest globalStatisticsRequest);
}
