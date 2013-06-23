package com.digdes.pktb.services;

import com.digdes.pktb.BaseTest;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.ReportRightsBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetByParentReportIdRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.impl.ReportImpl;
import com.digdes.pktb.persistence.services.MarshalDataService;
import com.digdes.pktb.persistence.services.RZDReportsService;
import com.digdes.pktb.persistence.services.ReportsService;
import com.google.gson.Gson;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kozlov.D
 * Date: 25.09.12
 * Time: 13:34
 */
public class ReportServiceTest extends BaseTest {

    @Autowired
    ReportsService reportsService;

    @Autowired
    RZDReportsService rzdReportsService;

    @Autowired
    MarshalDataService marshalDataService;

    @Test
    public void getReport() {
        String retrieveReport = "<retrieveReportReturn><attributes><Pair><name>vidT</name><value>РІСЃРµ</value></Pair><Pair><name>vidE</name><value>РўСЏРіРѕРІС‹Рµ РµРґРёРЅРёС†С‹</value></Pair><Pair><name>edIsp</name><value>РџРѕ РґРµРїРѕ РїСЂРёРїРёСЃРєРё</value></Pair><Pair><name>rwName</name><value>РЎР•Р’Р•Р РќРђРЇ                 </value></Pair><Pair><name>timeS</name><value>2012-09-27 03:00:00.0</value></Pair></attributes><tables><Table><rows><Row><values><string>1</string><string>BOPK.      </string><string>41|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>32|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>18|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>2|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>7|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>9|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>7|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string><string>2|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=24&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=BOPK.      &amp;p0=*</string></values></Row><Row><values><string>2</string><string>РџEР§OP      </string><string>199|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>149|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>82|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>47|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>15|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>11|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>9|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>49|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string><string>1|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=22&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РџEР§OP      &amp;p0=*</string></values></Row><Row><values><string>3</string><string>COCHР“      </string><string>47|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>40|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>28|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>3|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>25|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>7|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=21&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=COCHР“      &amp;p0=*</string></values></Row><Row><values><string>4</string><string>KOT-Р®      </string><string>272|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>221|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>162|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>83|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>43|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>23|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>13|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>51|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=19&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=KOT-Р®      &amp;p0=*</string></values></Row><Row><values><string>5</string><string>Р\u0098CAK.      </string><string>96|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>87|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>60|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>17|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>13|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>12|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>18|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>9|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=15&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098CAK.      &amp;p0=*</string></values></Row><Row><values><string>6</string><string>HРЇHР”.      </string><string>62|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>55|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>41|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>1|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>1|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>20|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>19|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>6|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string><string>1|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=13&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=HРЇHР”.      &amp;p0=*</string></values></Row><Row><values><string>7</string><string>Р›РћРЎРўРђ      </string><string>294|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>278|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>209|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>152|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>3|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>6|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>48|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>16|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=11&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р›РћРЎРўРђ      &amp;p0=*</string></values></Row><Row><values><string>8</string><string>Р‘РЈР™        </string><string>146|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>116|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>93|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>56|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>15|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>22|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>9|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string><string>21|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=6&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р‘РЈР™        &amp;p0=*</string></values></Row><Row><values><string>9</string><string>Р\u0098B-C.      </string><string>142|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>111|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>75|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>28|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>22|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>13|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>12|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>22|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>1|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string><string>8|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=5&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р\u0098B-C.      &amp;p0=*</string></values></Row><Row><values><string>10</string><string>РЇP.Р“Р›      </string><string>203|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>172|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>110|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>67|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>1|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>4|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>38|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>29|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string><string>2|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=1&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=РЇP.Р“Р›      &amp;p0=*</string></values></Row><Row><values><string>11</string><string>Р”РѕСЂРѕРіР°</string><string>1502|p1=2012-09-27 3:0:00.0&amp;p5=00&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>1261|p1=2012-09-27 3:0:00.0&amp;p5=01&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>878|p1=2012-09-27 3:0:00.0&amp;p5=02&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>451|p1=2012-09-27 3:0:00.0&amp;p5=03&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>100|p1=2012-09-27 3:0:00.0&amp;p5=04&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>114|p1=2012-09-27 3:0:00.0&amp;p5=05&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>213|p1=2012-09-27 3:0:00.0&amp;p5=06&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>0|p1=2012-09-27 3:0:00.0&amp;p5=07&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>205|p1=2012-09-27 3:0:00.0&amp;p5=10&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>1|p1=2012-09-27 3:0:00.0&amp;p5=31&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string><string>35|p1=2012-09-27 3:0:00.0&amp;p5=32&amp;p3=28&amp;p4=0&amp;p5=00&amp;p6=28&amp;p2=*&amp;id=2&amp;p7=*&amp;p8=1&amp;p9=Р”РѕСЂРѕРіР°&amp;p0=*</string></values></Row></rows></Table></tables><reportOutputTemplate>\n" +
                "\t<title>Р’С‹С…РѕРґРЅРѕР№ РѕС‚С‡РµС‚</title>\n" +
                "    <tables>\n" +
                "        <table>\n" +
                "            <headers>\n" +
                "                <rows>\n" +
                "                    <row>\n" +
                "                        <headerCells>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>2</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>Р”РµРїРѕ РїСЂРёРїРёСЃРєРё</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>2</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>Р\u0098РЅРІ. РїР°СЂРє (РІСЃРµРіРѕ)</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>1</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>Р­РєСЃРїР»СѓР°С‚РёСЂСѓРµРјС‹Р№ РїР°СЂРє</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>2</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>РќР­Рџ (РІСЃРµРіРѕ)</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>2</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>РђСЂРµРЅРґР°</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>2</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>Р—Р°РїР°СЃ</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "\n" +
                "                        </headerCells>\n" +
                "                    </row>\n" +
                "                    <row>\n" +
                "                        <headerCells>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>1</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>Р’СЃРµРіРѕ</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>1</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>Р“СЂ.</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>1</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>РџР°СЃСЃ.</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>1</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>РҐРѕР·.</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>1</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>РњР°РЅ.</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                            <headerCell>\n" +
                "                                <rowspan>1</rowspan>\n" +
                "                                <colspan>1</colspan>\n" +
                "                                <headerText>РџСЂ.</headerText>\n" +
                "                                <headerKey/>\n" +
                "                            </headerCell>\n" +
                "                        </headerCells>\n" +
                "                    </row>\n" +
                "                </rows>\n" +
                "            </headers>\n" +
                "        </table>\n" +
                "    </tables>\n" +
                "</reportOutputTemplate></retrieveReportReturn>";
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
                        byteArrayOutputStream = marshalDataService.getReportHTML(new StreamSource(new StringReader(retrieveReport)));
                        retrieveReport = byteArrayOutputStream.toString("utf-8");
                        retrieveReport = retrieveReport.substring(StringUtils.indexOf(retrieveReport, ">") + 1);
            System.out.println(retrieveReport);
                    } catch (TransformerException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (SAXException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (ConfigurationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
    }

    @Test
    public void mergXML(){
        Report report = new ReportImpl();//reportsService.getReportById(409L);
        report.setOutputTemplatePath("D:\\Kozlov\\projects\\pktb_project\\checkout\\PKTB_Project\\trunk\\webPKTB\\src\\main\\resources\\inputTemplates\\inputTemplate_409.xml");

        String str = rzdReportsService.mergeXMLs(report, "<rootElem><elem1></elem1><elem2></elem2></rootElem>");
        System.out.println(str);
    }

    @Test
    public void addRights(){
        Gson gson = new Gson();
        reportsService.addRights(gson.fromJson("{\"report\":{\"id\":439},\"user\":{\"userId\":2},\"rightsId\":13}", ReportRightsBean.class));
    }

    @Test
    public void getByParentReportId(){
        Gson gson = new Gson();
        String request= "{\"id\":\"415\",\"userBean\":{\"userRoleBean\":{\"roleName\":\"Администратор\",\"roleKey\":\"admin\"},\"displayName\":\"Кунегин Игорь Валерьевич\",\"cn\":\"Кунегин Игорь Валерьевич\",\"railway\":{\"code\":\"01\",\"name\":\"Октябрьская\"},\"userId\":4}}";
        GetByParentReportIdRequest getByParentReportIdRequest = gson.fromJson(request, GetByParentReportIdRequest.class);
        for (ReportDTO reportDTO :reportsService.getByParentReportId(getByParentReportIdRequest)){
            System.out.println(reportDTO.getName()+" "+reportDTO.getId());
        }
    }

    @Test
    public void createReportIndexes(){
//        reportsService.createReportIndexes(null, (long) 1);
    }

}
