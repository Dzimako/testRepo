package com.digdes.pktb;


import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GlobalStatisticsRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.StatisticsResponse;
import com.digdes.pktb.persistence.model.ReportStat;
import com.digdes.pktb.persistence.services.ReportStatService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ReportStatTest extends BaseTest {

    @Autowired
    ReportStatService reportStatService;

    @Test
    public void getReportStatByReportId() {
        for(StatisticsResponse statisticsResponse :reportStatService.getReportStatByReportId((long) 409)){
            System.out.println("statisticsResponse.getSuccess(): "+statisticsResponse.getSuccess());
        }
    }

    @Test
    public void getGlobalReportStat() {
        GlobalStatisticsRequest globalStatisticsRequest = new GlobalStatisticsRequest();
        globalStatisticsRequest.setDateOfDownloadBegin(new Date());
        globalStatisticsRequest.setDateOfDownloadEnd(new Date());
        globalStatisticsRequest.setSuccess(true);
        for(StatisticsResponse statisticsResponse :reportStatService.getGlobalReportStat(globalStatisticsRequest)){
            System.out.println("statisticsResponse.getSuccess(): "+statisticsResponse.getSuccess());
        }
    }
}