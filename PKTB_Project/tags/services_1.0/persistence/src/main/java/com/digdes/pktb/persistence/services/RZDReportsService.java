package com.digdes.pktb.persistence.services;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.outputModel.TableBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetReportRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.GetReportResponse;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.beans.wsbeans.requests.ConsumerBean;
import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;
import com.digdes.pktb.persistence.beans.wsbeans.responses.DictionaryResponse;
import com.digdes.pktb.persistence.beans.wssupport.CreateReportResult;
import com.digdes.pktb.persistence.beans.wssupport.Header;
import com.digdes.pktb.persistence.beans.wssupport.RetrieveReportResult;
import com.digdes.pktb.persistence.model.Report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 18.09.12
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public interface RZDReportsService {

    CreateReportResult createReportWithConsumer(ConsumerBean consumerBean, String reportName, List<Pair> params);

    RetrieveReportResult retrieveReportWithConsumer(ConsumerBean consumerBean, String reportId);

    DictionaryResponse getDictionary(UserBean userBean, String dictionaryUid);

    GetReportResponse getReport(UserBean userBean, Long reportName, List<Pair> params);

    String mergeXMLs(Report report, String retriveReport);

    String getFiltersHTML(ReportDTO reportDTO) throws IOException;

    GetReportResponse getReportFromDB(String datasource, String proc_name, List<Pair> parameters, Long reportId, GetReportRequest getReportRequest);

    public GetReportResponse getReportExcelXml(UserBean userBean, Long reportName, List<Pair> params) ;
}
