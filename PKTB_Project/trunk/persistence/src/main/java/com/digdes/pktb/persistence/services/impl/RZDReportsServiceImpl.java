package com.digdes.pktb.persistence.services.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.outputModel.*;
import com.digdes.pktb.persistence.beans.ajaxbeans.outputModel.Exception;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetReportRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.GetReportResponse;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.beans.wsbeans.requests.*;
import com.digdes.pktb.persistence.beans.wsbeans.responses.CreateReportResponse;
import com.digdes.pktb.persistence.beans.wsbeans.responses.DictionaryResponse;
import com.digdes.pktb.persistence.beans.wssupport.CreateReportResult;
import com.digdes.pktb.persistence.beans.wssupport.RetrieveReportResult;
import com.digdes.pktb.persistence.beans.xml.ParameterXML;
import com.digdes.pktb.persistence.beans.xml.RetrievedReportXML;
import com.digdes.pktb.persistence.beans.xml.RowXML;
import com.digdes.pktb.persistence.beans.xml.TableXML;
import com.digdes.pktb.persistence.dao.DictionaryDao;
import com.digdes.pktb.persistence.dao.ReportDao;
import com.digdes.pktb.persistence.dao.ReportStatDao;
import com.digdes.pktb.persistence.dao.UserDao;
import com.digdes.pktb.persistence.dao.impl.RZDReportDaoImpl;
import com.digdes.pktb.persistence.helpers.PKTBHelper;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.ReportStat;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.impl.ReportStatImpl;
import com.digdes.pktb.persistence.services.MarshalDataService;
import com.digdes.pktb.persistence.services.RZDReportsService;
import com.digdes.pktb.persistence.util.Calculator;
import com.digdes.pktb.persistence.util.ProcedureResultSet;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.util.StringHelper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 18.09.12
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class RZDReportsServiceImpl extends WebServiceGatewaySupport implements RZDReportsService {


    private MarshalDataService marshalDataService;
    private ReportDao reportDao;
    private ReportStatDao reportStatDao;
    private UserDao userDao;
    private RZDReportDaoImpl rzdReportDao;
    private DictionaryDao dictionaryDao;
    private static Logger logger = Logger.getLogger("debug-log-file");

    public void setMarshalDataService(MarshalDataService marshalDataService) {
        this.marshalDataService = marshalDataService;
    }

    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public void setReportStatDao(ReportStatDao reportStatDao) {
        this.reportStatDao = reportStatDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setRzdReportDao(RZDReportDaoImpl rzdReportDao) {
        this.rzdReportDao = rzdReportDao;
    }

    public void setDictionaryDao(DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    private Source marshall(RetrieveReportRequest retrieveReportRequest) throws IOException {

        StringResult result = new StringResult();

        getMarshaller().marshal(retrieveReportRequest, result);

        return new ResourceSource(new ByteArrayResource(result.toString().getBytes()));
    }

    public CreateReportResult createReportWithConsumer(ConsumerBean consumerBean, String reportName, List<Pair> params) {
        CreateReportResult createReportResult = new CreateReportResult();
        /*if (paramsMap == null)
            paramsMap = ParamsHelper.getParamsMapByKey(reportName);
        if (paramsMap == null)
            return createReportResult;*/

        CreateReportRequest createReportRequest = new CreateReportRequest();
        if (consumerBean != null)
            createReportRequest.setConsumer(consumerBean);
        Request request = new Request();
        request.setReportName(reportName);
//        Set<Pair> params = new HashSet<Pair>();
        /*for (String key : paramsMap.keySet()) {
            params.add(new Pair(key, paramsMap.get(key)));
        }*/
        request.setParams(params);
        createReportRequest.setRequest(request);
        CreateReportResponse createReportResponse = (CreateReportResponse) getWebServiceTemplate().marshalSendAndReceive(createReportRequest);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        try {
            getWebServiceTemplate().getMarshaller().marshal(createReportRequest, result);
        } catch (IOException e) {
            logger.error("Error: ",e);
        }
        logger.debug("sw: " + sw.toString());
//        System.out.println("sw: " + sw.toString());
        createReportResult.setResult(createReportResponse.getCreateReportReturn());
        createReportResult.setSizeOfRequest((long) sw.toString().getBytes().length);
        return createReportResult;
    }

    public RetrieveReportResult retrieveReportWithConsumer(ConsumerBean consumerBean, String reportId) {
        RetrieveReportResult retrieveReportResult = new RetrieveReportResult();
        retrieveReportResult.setSizeOfResponse((long) 0);
        RetrieveReportRequest retrieveReportRequest = new RetrieveReportRequest();
        if (consumerBean != null)
            retrieveReportRequest.setConsumer(consumerBean);
        retrieveReportRequest.setUid(reportId);
        StreamResult result = new StreamResult(new StringWriter());
        Date requestBegin = new Date();
        while (((new Date().getTime() - requestBegin.getTime()) < Long.parseLong(PKTBHelper.getSettingsValue("rzdservice.timeToWait")))) {
            retrieveReportResult.setErrorMessage(PKTBHelper.getMessage("retrieveReportResult.errorMessage"));
            try {
                getWebServiceTemplate().sendSourceAndReceiveToResult(marshall(retrieveReportRequest), result);
                StringWriter sw = (StringWriter) result.getWriter();
                retrieveReportResult.setResult(getBodyString(sw.getBuffer().toString()));
                retrieveReportResult.setSizeOfResponse((long) sw.toString().getBytes().length);
                return retrieveReportResult;
            } catch (Throwable e) {
                logger.debug("Error while retrieveReportWithConsumer: " + e.getMessage());
//                System.out.println("e.getMessage(): " + e.getMessage());
                if (!e.getMessage().contains("com.epam.rzd.ws.exception.NoResultException")) {
                    retrieveReportResult.setErrorMessage(e.getMessage());
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return retrieveReportResult;
    }

    public DictionaryResponse getDictionary(UserBean userBean, String dictionaryUid) {
        if (userBean == null || userBean.getUserId() == null)
            return null;
        DictionaryRequest dictionaryRequest = new DictionaryRequest();
        User user = userDao.load(userBean.getUserId());
        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setRailwayCode(user.getRailway().getCode());
        consumerBean.setUser(user.getCn());
        consumerBean.setSystem(PKTBHelper.getSettingsValue("rzdservice.consumer.system"));
        consumerBean.setUserIp(PKTBHelper.getSettingsValue("rzdservice.consumer.userip"));
        dictionaryRequest.setConsumer(consumerBean);
        dictionaryRequest.setUid(dictionaryUid);
        System.out.println("dictionaryRequest: ");
        StreamResult result = new StreamResult(System.out);
        try {
            getWebServiceTemplate().getMarshaller().marshal(dictionaryRequest, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (DictionaryResponse) getWebServiceTemplate().marshalSendAndReceive(dictionaryRequest);
    }

    public GetReportResponse getReport(UserBean userBean, Long reportName, List<Pair> params) {
        Report report = reportDao.getById(reportName);
        User user = userDao.load(userBean.getUserId());
        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setRailwayCode(user.getRailway().getCode());
        consumerBean.setUser(user.getCn());
        consumerBean.setSystem(PKTBHelper.getSettingsValue("rzdservice.consumer.system"));
        consumerBean.setUserIp("rzdservice.consumer.userip");
        logger.debug("report.getUid(): " + report.getUid());

//        String key = createReport(report.getUid(), paramsMap);
        CreateReportResult createReportResult = createReportWithConsumer(consumerBean, report.getUid(), params);
//        System.out.println("retrieveReport key: " + createReportResult.getResult());
        logger.debug("retrieveReport key: " + createReportResult.getResult());
        logger.debug("createReportResult sizeOfRequest: " + createReportResult.getSizeOfRequest());
//        System.out.println("createReportResult sizeOfRequest: " + createReportResult.getSizeOfRequest());
        RetrieveReportResult retrieveReportResult = retrieveReportWithConsumer(consumerBean, createReportResult.getResult());
        logger.debug("retrieveReportResult getSizeOfResponse: " + retrieveReportResult.getSizeOfResponse());
//        System.out.println("retrieveReportResult getSizeOfResponse: " + retrieveReportResult.getSizeOfResponse());

        String retrieveReport = retrieveReportResult.getResult();
//         logger.debug("Returned report: " + retrieveReport);
//        System.out.println("retrieveReport: " + retrieveReport);
        try {
            retrieveReport = removeTroublesomeCharacters(retrieveReport);
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        retrieveReport = mergeXMLs(report, retrieveReport);
//        logger.debug("Merged XML: " + retrieveReport);
//        System.out.println("Merged XML: " + retrieveReport);
        if (retrieveReport != null) {
            saveReportStat(report, user, true, createReportResult.getSizeOfRequest(), retrieveReportResult.getSizeOfResponse());
        } else {
            saveReportStat(report, user, false, createReportResult.getSizeOfRequest(), retrieveReportResult.getSizeOfResponse());
        }

        Boolean success = true;
        ByteArrayOutputStream byteArrayOutputStream = null;
        if (retrieveReport != null) {
            try {
                byteArrayOutputStream = marshalDataService.getReportHTML(new StreamSource(new StringReader(retrieveReport)));
                retrieveReport = byteArrayOutputStream.toString("utf-8");
                retrieveReport = retrieveReport.substring(StringUtils.indexOf(retrieveReport, ">") + 1);
                byteArrayOutputStream.close();
            } catch (Throwable e) {
                logger.error("Error while getting report: ", e);
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            retrieveReport = retrieveReportResult.getErrorMessage();
            success = false;
        }
        GetReportResponse getReportResponse = new GetReportResponse();
        getReportResponse.setResponse(retrieveReport);
        getReportResponse.setSuccess(success);
        return getReportResponse;
    }

    public static String removeTroublesomeCharacters(String inString) {
        if (inString == null) return null;

        StringBuilder newString = new StringBuilder();
        char ch;

        for (int i = 0; i < inString.length(); i++) {

            ch = inString.charAt(i);
            // remove any characters outside the valid UTF-8 range as well as all control characters
            // except tabs and new lines
            if ((ch >= 0x20) || ch == '\t' || ch == '\n' || ch == '\r') {
                newString.append(ch);
            }
        }
        return newString.toString();

    }

    private String convertXMLreportToHTML(String reportXML, Long reportId) {
        Report report = null;
        if (reportId != null) {
            report = reportDao.getById(reportId);
        }
        try {
            reportXML = removeTroublesomeCharacters(reportXML);
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (report != null) {
            reportXML = mergeXMLs(report, reportXML);
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        if (reportXML != null) {
            try {
                byteArrayOutputStream = marshalDataService.getReportHTML(new StreamSource(new StringReader(reportXML)));
                reportXML = byteArrayOutputStream.toString("utf-8");
                reportXML = reportXML.substring(StringUtils.indexOf(reportXML, ">") + 1);
                byteArrayOutputStream.close();
            } catch (Throwable e) {
                logger.error("Error while convertXMLreportToHTML: ", e);
            } finally {
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        logger.error("Error while closing byteArrayOutputStream: ", e);
                    }
                }
            }
        }
        return reportXML;
    }

    public GetReportResponse getReportExcelXml(UserBean userBean, Long reportName, List<Pair> params) {
        Report report = reportDao.getById(reportName);
        GetReportResponse response = getReport(userBean, reportName, params);
        response.setResponse("<div>" + "<div><b>" + report.getName() +  "</b></div>" + "<div>&#160;</div>" + response.getResponse().replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("\u0026amp;nbsp;", " ")   + "</div>");

        return response;
    }

    public String mergeXMLs(Report report, String retriveReport) {
        try {
            InputStream inStrOutputTemplate = new FileInputStream(PKTBHelper.getSettingsValue("reportTemplates.filesystem.rootPath") + report.getOutputTemplatePath());
            XMLBuilder builderTemplate = XMLBuilder.parse(new InputSource(inStrOutputTemplate));
            XMLBuilder builderRetrieve = XMLBuilder.parse(new InputSource(new StringReader(retriveReport)));
            Node element = builderTemplate.getElement().cloneNode(true);
            Node firstDocImportedNode = builderRetrieve.getDocument().importNode(element, true);
            builderRetrieve.getElement().appendChild(firstDocImportedNode);
            StringWriter sw = transformElement(builderRetrieve.getElement());
            return sw.toString();
        } catch (Throwable e) {
            logger.error("Error while merging xmls: ", e);
        }
        return retriveReport;
    }

    private void saveReportStat(Report report, User user, Boolean success, Long sizeOfRequest, Long sizeOfResponse) {
        logger.debug("saveReportStat report:" + report);
        ReportStat reportStat = new ReportStatImpl();
        reportStat.setDateOfDownload(Calendar.getInstance().getTime());
        reportStat.setUser(user);
        reportStat.setReport(report);
        reportStat.setSuccess(success);
        reportStat.setRequestSize(sizeOfRequest);
        reportStat.setResponseSize(sizeOfResponse);
        reportStatDao.saveReportStat(reportStat);
    }

    private String getBodyString(String result) {
        try {
            XMLBuilder builder = XMLBuilder.parse(
                    new InputSource(new StringReader(result)));
            Element element = builder.xpathFind("//retrieveReportReturn").getElement();
            StringWriter sw = transformElement(element);
            return sw.toString();
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private StringWriter transformElement(Element element) throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter sw = new StringWriter();
        t.transform(new DOMSource(element), new StreamResult(sw));
        return sw;
    }

    public String getFiltersHTML(ReportDTO reportDTO) throws IOException {
        Report report = null;
        if (reportDTO != null && reportDTO.getId() != null) {
            report = reportDao.load(reportDTO.getId());
        }
//        InputStream inStr = new FileInputStream("D:\\Kozlov\\projects\\pktb_project\\checkout\\PKTB_Project\\trunk\\webPKTB\\src\\main\\resources\\testInputTemplate.xml");
//        ClassLoader loader = RZDReportsServiceImpl.class.getClassLoader();
//        InputStream inStr = loader.getResourceAsStream("testInputTemplate.xml");
        InputStream inStr = null;
        if (report != null) {
            logger.debug("Path to inputTemplate: " + PKTBHelper.getSettingsValue("reportTemplates.filesystem.rootPath") + report.getInputTemplatePath());
            inStr = new FileInputStream(PKTBHelper.getSettingsValue("reportTemplates.filesystem.rootPath") + report.getInputTemplatePath());
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = marshalDataService.createFiltersHTML(new StreamSource(inStr));
            String filters = byteArrayOutputStream.toString("utf-8");
            filters = filters.substring(StringUtils.indexOf(filters, ">") + 1);
            return filters;
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
        return null;
    }

    public GetReportResponse getReportFromDB(GetReportRequest getReportRequest) { //String datasource, String proc_name, List<Pair> parameters, Long reportId,
        Map<String, ProcedureResultSet> resultSetMap = new HashMap<String, ProcedureResultSet>();
        if(getReportRequest.getResultSets() != null){
            for(ResultSetBean resultSetBean : getReportRequest.getResultSets()){
                List<Pair> params = getReportRequest.getPairList();
                if(resultSetBean.getParams() != null){
                    params = getParamsToCertainRequest(resultSetBean.getParams(), getParametersMappedByName(getReportRequest.getPairList()));
                }
                resultSetMap.put(resultSetBean.getKey(), rzdReportDao.callProcedure(resultSetBean.getDatasource(), resultSetBean.getProc_name(), params));
            }
        }
        ProcedureResultSet procedureResultSet = rzdReportDao.callProcedure(getReportRequest.getDatasource(), getReportRequest.getProc_name(), getReportRequest.getPairList());
        RetrievedReportXML retrievedReportXML = new RetrievedReportXML();
        if (getReportRequest.getAttributesResultSet() != null) {
            ProcedureResultSet procResult = resultSetMap.get(getReportRequest.getAttributesResultSet().getKey());
            retrievedReportXML.setAttributes(getParameterXMLsFromResult(procResult.getOutParamValues()));
        } else {
            retrievedReportXML.setAttributes(getParameterXMLsFromResult(procedureResultSet.getOutParamValues()));
        }
        if(resultSetMap.values() != null && !resultSetMap.values().isEmpty()){
            retrievedReportXML.setTables(getTableXMLsFromResult(procedureResultSet, getReportRequest));
        } else {
            retrievedReportXML.setTables(getTableXMLsFromResult(procedureResultSet, getReportRequest));
        }
        logger.debug("ReportXML object: " + retrievedReportXML);
//        System.out.println("ReportXML object: " + retrievedReportXML);
        String retrivedReportXMLStr = marshalDataService.getXMLFromReport(retrievedReportXML);
        logger.debug("ReportXML string: " + retrivedReportXMLStr);
//        System.out.println("ReportXML string: " + retrivedReportXMLStr);
        GetReportResponse getReportResponse = new GetReportResponse();
        getReportResponse.setResponse(convertXMLreportToHTML(retrivedReportXMLStr, getReportRequest.getReportId()));
        logger.debug("HTML response: " + getReportResponse.getResponse());
//        System.out.println("HTML response: " + getReportResponse.getResponse());
        getReportResponse.setSuccess(true);
        return getReportResponse;
    }

    private Map<String, Pair> getParametersMappedByName(List<Pair> parameters){
        Map<String, Pair> mappedParams = new HashMap<String, Pair>();
        if(parameters != null){
            for(Pair param : parameters){
                mappedParams.put(param.getName(), param);
            }
        }
        return mappedParams;
    }

    private List<Pair> getParamsToCertainRequest(List<Pair> paramsToAdd, Map<String, Pair> allParams){
        List<Pair> params = new ArrayList<Pair>();
        for(Pair paramToAdd : paramsToAdd){
            if(allParams.containsKey(paramToAdd.getName())){
                Pair globalParam = allParams.get(paramToAdd.getName());
                params.add(new Pair(globalParam.getName(), globalParam.getValue(), paramToAdd.getInputParam(), globalParam.getOrderNum(), globalParam.getParamDataType(), globalParam.getMarkerSQLCode()));
            } else {
                params.add(paramToAdd);
            }
        }
        return params;
    }

    private Map<String, Map<String, String>> getDictionariesMappedByKey(GetReportRequest getReportRequest) {
        List<DictionaryBean> dictionaryBeans = getReportRequest.getDictionaries();
        Map<String, Map<String, String>> mapDictionariesByKey = new HashMap<String, Map<String, String>>();
        logger.debug("Dictionaries:");
        logger.debug(dictionaryBeans);
        logger.debug("importedDictionaries:");
        logger.debug(getReportRequest.getImportedDictionaries());
        for (String importedDictionary : getReportRequest.getImportedDictionaries()) {
            logger.debug("Search importedDictionary with key: "+importedDictionary);
            logger.debug("dictionaryBeans.contains(): "+dictionaryBeans.contains( new DictionaryBean(importedDictionary)));
            Integer dictionaryBeanIndex = dictionaryBeans.indexOf( new DictionaryBean(importedDictionary));
            logger.debug("Found dictionaryBeanIndex = "+ dictionaryBeanIndex);
            if (dictionaryBeanIndex >= 0) {
                DictionaryBean dictionaryBean = dictionaryBeans.get(dictionaryBeanIndex);
                Map<String, String> dictionaryMap = dictionaryDao.executeDictionaryRequest(dictionaryBean.getDataSource(), dictionaryBean.getFilterDBkey(), dictionaryBean.getFilterDBkeyValue(), dictionaryBean.getDatabaseTable());
                if (dictionaryBean.getExceptions() != null && dictionaryBean.getExceptions().size() != 0){
                    for (Exception exception: dictionaryBean.getExceptions()){
                        logger.debug("put in dictionaryMap exception: exception.getKey()= "+exception.getKey()+" exception.getValue()"+exception.getValue());
                        dictionaryMap.put(exception.getKey().trim(), exception.getValue().trim());
                    }
                }
                logger.debug("importedDictionary: ");
                logger.debug(importedDictionary);
                logger.debug("Dictionary: ");
                logger.debug(dictionaryMap);
                mapDictionariesByKey.put(importedDictionary, dictionaryMap);
            }
        }
        return mapDictionariesByKey;
    }

    private List<ParameterXML> getParameterXMLsFromResult(List<String> outParamValues) {
        List<ParameterXML> parametersXML = new ArrayList<ParameterXML>();
        if (outParamValues != null) {
            Integer outParamNum = 0;
            for (String outParamValue : outParamValues) {
                parametersXML.add(new ParameterXML("head" + outParamNum, outParamValue));
                outParamNum++;
            }
        }
        return parametersXML;
    }

    private List<TableXML> getTableXMLsFromResult(ProcedureResultSet procedureResultSet, GetReportRequest getReportRequest) {
        List<TableXML> tables = new ArrayList<TableXML>();
        Map<String, Map<String, String>> mapDictionariesByKey = getDictionariesMappedByKey(getReportRequest);
        Calculator calculator = new Calculator(procedureResultSet, mapDictionariesByKey);
        if (procedureResultSet != null) {
            int numColumns = procedureResultSet.getNumOfColumns();
            if (getReportRequest.getTableBeans() != null) {
                for (TableBean tableBean : getReportRequest.getTableBeans()) {
                    TableXML tableXML = new TableXML();
                    List<RowXML> rowXMLs = new ArrayList<RowXML>();
                    Integer rowIndex = 1;
                    procedureResultSet.beforeFirst();
                    while (procedureResultSet.next()) {
                        RowXML rowXML = new RowXML();
                        List<String> values = new ArrayList<String>();
                        if (tableBean.getColumns() != null && tableBean.getColumns().size() > 0) {
                            for (ColumnBean columnBean : tableBean.getColumns()) {
                                String valueToAdd = calculator.calculateCellValue(columnBean.getOperator(), rowIndex, null);
                                if (valueToAdd == null || valueToAdd.equalsIgnoreCase("")) {
                                    valueToAdd = "-";
                                }
                                values.add(valueToAdd);
                            }
                        } else if(tableBean.getRowFunctions() == null || tableBean.getRowFunctions().size() == 0){
                            if (rowIndex == 1) {
                                if (procedureResultSet.getColumnNames() != null) {
                                    rowXMLs.add(new RowXML(procedureResultSet.getColumnNames()));
                                }
                                /*int columNumber = 0;
                                while (columNumber <= numColumns) {
                                    values.add(procedureResultSet.getColumnValue(columNumber));
                                    columNumber++;
                                }*/
                            }
                            int columNumber = 1;
                            while (columNumber <= numColumns) {
                                values.add(procedureResultSet.getColumnValue(columNumber));
                                columNumber++;
                            }
                        }
                        rowXML.setValues(values);
                        rowXMLs.add(rowXML);
                        rowIndex++;
                    }

                    if (tableBean.getRowFunctions() != null && tableBean.getRowFunctions().size() > 0) {

                        for (RowFunctionBean rowFunctionBean : tableBean.getRowFunctions()) {
                            logger.debug("RowFunction: " + rowFunctionBean);
                            rowXMLs.addAll(calculator.calculateRowFunctionRows(rowFunctionBean));

                        }
                    }
                    tableXML.setRows(rowXMLs);
                    tables.add(tableXML);
                }
            }
        }

        return tables;
    }


    private List<TableXML> getTableXMLsFromResult(Map<String, ProcedureResultSet> resultSetsMap, GetReportRequest getReportRequest) {
        List<TableXML> tables = new ArrayList<TableXML>();
        Map<String, Map<String, String>> mapDictionariesByKey = getDictionariesMappedByKey(getReportRequest);
//        Calculator calculator = new Calculator(procedureResultSet, mapDictionariesByKey);
        if (resultSetsMap != null) {

            if (getReportRequest.getTableBeans() != null) {
                for (TableBean tableBean : getReportRequest.getTableBeans()) {
                    ProcedureResultSet procedureResultSet = resultSetsMap.get(tableBean.getInputResultSet().getKey());
                    if (procedureResultSet != null) {
                        Calculator calculator = new Calculator(procedureResultSet, resultSetsMap, mapDictionariesByKey);
                        TableXML tableXML = new TableXML();
                        List<RowXML> rowXMLs = new ArrayList<RowXML>();
                        int numColumns = procedureResultSet.getNumOfColumns();
                        Integer rowIndex = 1;
                        procedureResultSet.beforeFirst();
                        while (procedureResultSet.next()) {
                            RowXML rowXML = new RowXML();
                            List<String> values = new ArrayList<String>();
                            if (tableBean.getColumns() != null && tableBean.getColumns().size() > 0) {
                                for (ColumnBean columnBean : tableBean.getColumns()) {
                                    String valueToAdd = calculator.calculateCellValue(columnBean.getOperator(), rowIndex, null);
                                    if (valueToAdd == null || valueToAdd.equalsIgnoreCase("")) {
                                        valueToAdd = "-";
                                    }
                                    values.add(valueToAdd);
                                }
                            } else if (tableBean.getRowFunctions() == null || tableBean.getRowFunctions().size() == 0) {
                                if (rowIndex == 1) {
                                    if (procedureResultSet.getColumnNames() != null) {
                                        rowXMLs.add(new RowXML(procedureResultSet.getColumnNames()));
                                    }
                                    int columNumber = 0;
                                    while (columNumber <= numColumns) {
                                        values.add(procedureResultSet.getColumnValue(columNumber));
                                        columNumber++;
                                    }
                                }
                                int columNumber = 1;
                                while (columNumber <= numColumns) {
                                    values.add(procedureResultSet.getColumnValue(columNumber));
                                    columNumber++;
                                }
                            }
                            rowXML.setValues(values);
                            rowXMLs.add(rowXML);
                            rowIndex++;
                        }
                        resultSetsMap.put(tableBean.getInputResultSet().getKey(), createProcedureResultSet(rowXMLs));


                        if (tableBean.getRowFunctions() != null && tableBean.getRowFunctions().size() > 0) {
                            for (RowFunctionBean rowFunctionBean : tableBean.getRowFunctions()) {
                                ProcedureResultSet resultSetToUse = procedureResultSet;
                                        if(rowFunctionBean.getResultSetToUse() != null){
                                            if(resultSetsMap.containsKey(rowFunctionBean.getResultSetToUse().getKey())){
                                                resultSetToUse = resultSetsMap.get(rowFunctionBean.getResultSetToUse().getKey());
                                            }
                                        }
                                Calculator calculatorForRowFunc = new Calculator(resultSetToUse, resultSetsMap, mapDictionariesByKey);
                                logger.debug("RowFunction: " + rowFunctionBean);
                                rowXMLs.addAll(calculatorForRowFunc.calculateRowFunctionRows(rowFunctionBean));
                                resultSetsMap.put(tableBean.getInputResultSet().getKey(), createProcedureResultSet(rowXMLs));
                            }
                        }
                        tableXML.setRows(rowXMLs);
                        tables.add(tableXML);
                    }
                }
            }
        }

        return tables;
    }

    private ProcedureResultSet createProcedureResultSet(List<RowXML> rowXMLs){
        ProcedureResultSet procedureResultSet = new ProcedureResultSet();
        List<List<String>> resultSetData = new ArrayList<List<String>>();
        if(rowXMLs != null){
            for(RowXML rowXML : rowXMLs){
                if(rowXML.getValues() != null){
                    resultSetData.add(rowXML.getValues());
                }
            }
        }
        procedureResultSet.setResultSetData(resultSetData);
        return procedureResultSet;
    }


}
