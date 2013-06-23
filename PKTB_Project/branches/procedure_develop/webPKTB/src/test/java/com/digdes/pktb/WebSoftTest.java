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
        String requestBean = "{\"reportId\":445,\"userBean\":{\"userId\":3},\"pairList\":[{\"name\":\"period\",\"value\":\"1\"},{\"name\":\"dorPr\",\"value\":\"28\"},{\"name\":\"depoPr\",\"value\":\"*\"},{\"name\":\"W4_and_W5\",\"value\":\"*\"},{\"name\":\"vidT\",\"value\":\"*\"},{\"name\":\"W7\",\"value\":\"*\"},{\"name\":\"concrete_station\",\"value\":\"0\"},{\"name\":\"concrete_depo\",\"value\":\"0\"}]}";
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
//        String request= "{\"reportId\":506,\"userBean\":{\"userId\":3},\"datasource\":\"jdbc/pktb2\",\"proc_name\":\"SPP2978\",\"pairList\":[{\"name\":\"p2\",\"value\":\"0\",\"orderNum\":\"2\",\"inputParam\":\"in\",\"paramDataType\":\"12\"},{\"name\":\"p3\",\"value\":\"1\",\"orderNum\":\"3\",\"inputParam\":\"in\",\"paramDataType\":\"12\"},{\"name\":\"p1\",\"value\":\"01\",\"orderNum\":\"1\",\"inputParam\":\"in\",\"paramDataType\":\"12\"},{\"name\":\"P1\",\"value\":\"\",\"orderNum\":\"4\",\"inputParam\":\"out\",\"paramDataType\":\"5\"},{\"name\":\"P2\",\"value\":\"\",\"orderNum\":\"5\",\"inputParam\":\"out\",\"paramDataType\":\"4\"}],\"tableBeans\":[{\"inputResultSet\":{\"name\":\"\"},\"outputResultSet\":{\"name\":\"\"},\"columns\":[{\"operator\":{\"operatorKey\":\"iteration\"}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"1\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"2\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"3\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"4\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"5\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"6\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"7\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"8\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"9\"}]}},{\"operator\":{\"operatorKey\":\"column\",\"arguments\":[{\"columnNumber\":\"10\"}]}}],\"rowFunctions\":[]}],\"dictionaries\":[],\"importedDictionaries\":[]}";
//        Gson gson = new Gson();
//        GetReportRequest getReportRequest = gson.fromJson(request, GetReportRequest.class);
//        rzdReportsService.getReportFromDB(getReportRequest);
    }

}