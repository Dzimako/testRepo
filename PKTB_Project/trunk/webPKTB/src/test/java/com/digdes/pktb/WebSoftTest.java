package com.digdes.pktb;


import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetByParentReportIdRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetReportRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.GetReportResponse;
import com.digdes.pktb.persistence.beans.wsbeans.requests.ConsumerBean;
import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;
import com.digdes.pktb.persistence.beans.wssupport.CreateReportResult;
import com.digdes.pktb.persistence.helpers.ParamsHelper;
import com.digdes.pktb.persistence.services.RZDReportsService;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class WebSoftTest extends BaseTest {

    @Autowired
    RZDReportsService rzdReportsService;

    @Test
      public void testWebSoft() {
//        ConsumerBean consumerBean = new ConsumerBean();
//        consumerBean.setRailwayCode("28");
//        consumerBean.setSystem("testSystem");
//        consumerBean.setUser("testUser");
//        consumerBean.setUserIp("10.10.10.10");
//        CreateReportResult createReportResult = rzdReportsService.createReportWithConsumer(consumerBean, "spl3132", ParamsHelper.getParamsMapByKey("spl3132"));
//        System.out.println("createReportResult.getResult(): "+createReportResult.getResult());
//        System.out.println(rzdReportsService.retrieveReportWithConsumer(consumerBean, createReportResult.getResult()).getResult());
//        System.out.println(rzdReportsService.retrieveReportWithConsumer(consumerBean, createReportResult.getResult()).getErrorMessage());
    }

    @Test
    public void createReportWithConsumer() {
        Gson gson = new Gson();
        String requestBean = "{\"reportId\":446,\"userBean\":{\"userId\":3},\"pairList\":[]}";
        GetReportRequest getReportRequest = gson.fromJson(requestBean, GetReportRequest.class);
        GetReportResponse reportContent = rzdReportsService.getReport(getReportRequest.getUserBean(), getReportRequest.getReportId(), getReportRequest.getPairList());
        System.out.println(reportContent.getResponse());
    }

    /*@Test
    public void getDictionary() {
        UserBean userBean = new UserBean();
        userBean.setUserId((long) 2);
        String s = "dict_tn_sost_lok_id_1";
        System.out.println("key: "+s);
        for (Pair pair:rzdReportsService.getDictionary(userBean, s).getPairList()){
            System.out.println("pair pair.getName(): "+pair.getName()+";  pair.getValue():"+pair.getValue());
        }
    }*/

    @Test
    public void getReportFromDB(){
//        String request= "{\"reportId\":501,\"userBean\":{\"userId\":3},\"datasource\":\"jdbc/pktb2\",\"proc_name\":\"SPP3135\",\"pairList\":[{\"name\":\"dor\",\"value\":\"01\",\"orderNum\":\"1\",\"inputParam\":\"in\",\"paramDataType\":\"12\"},{\"name\":\"ngr\",\"orderNum\":\"2\",\"inputParam\":\"in\",\"paramDataType\":\"5\",\"value\":\"0\"},{\"name\":\"vgr\",\"orderNum\":\"3\",\"inputParam\":\"in\",\"paramDataType\":\"5\",\"value\":\"30000\"},{\"name\":\"P1\",\"value\":\"\",\"orderNum\":\"4\",\"inputParam\":\"out\",\"paramDataType\":\"5\"},{\"name\":\"P2\",\"value\":\"\",\"orderNum\":\"5\",\"inputParam\":\"out\",\"paramDataType\":\"4\"}]}";
//        Gson gson = new Gson();
//        GetReportRequest getReportRequest = gson.fromJson(request, GetReportRequest.class);
//        rzdReportsService.getReportFromDB(getReportRequest.getDatasource(), getReportRequest.getProc_name(), getReportRequest.getPairList(), getReportRequest.getReportId());
    }

}