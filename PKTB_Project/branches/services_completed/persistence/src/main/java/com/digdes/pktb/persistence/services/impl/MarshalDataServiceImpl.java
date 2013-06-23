package com.digdes.pktb.persistence.services.impl;

import com.digdes.pktb.persistence.beans.xml.RetrievedReportXML;
import com.digdes.pktb.persistence.services.MarshalDataService;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class MarshalDataServiceImpl implements MarshalDataService {

    private static final String CONF_FILE_NAME = "userconf.xml";
    private static Logger logger = Logger.getLogger("debug-log-file");
    private Jaxb2Marshaller jaxb2Marshaller;

    public void setJaxb2Marshaller(Jaxb2Marshaller jaxb2Marshaller) {
        this.jaxb2Marshaller = jaxb2Marshaller;
    }

    private void xmlToHtml(Source xml, InputStream xsl, OutputStream html) throws TransformerException {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            Transformer transformer =
                    tFactory.newTransformer
                            (new StreamSource
                                    (xsl));

            transformer.transform
                    (xml,
                            new javax.xml.transform.stream.StreamResult
                                    (html));
            html.flush();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    // Метод создания PDF реестра секретаря из входыщего xml описания

    public ByteArrayOutputStream getReportHTML(Source srcXML) throws TransformerException, IOException, SAXException, ConfigurationException {
        ByteArrayOutputStream outPDFStream = new ByteArrayOutputStream();
        try {
            ClassLoader loader = MarshalDataService.class.getClassLoader();
            InputStream xsl = loader.getResourceAsStream("xsl/plain_text_body.xsl");
            new MarshalDataServiceImpl().xmlToHtml(srcXML, xsl, outPDFStream);
        } catch (Throwable e) {
            logger.error("Error: ", e);
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        return outPDFStream;
    }

    public ByteArrayOutputStream createFiltersHTML(Source srcXML) throws TransformerException, IOException, SAXException, ConfigurationException {
        logger.debug("Creating filters HTML");
        //        System.out.println("Creating secretar pdf");
        ByteArrayOutputStream outHTMLStream = new ByteArrayOutputStream();
        try {
            ClassLoader loader = MarshalDataService.class.getClassLoader();
            InputStream xsl = loader.getResourceAsStream("xsl/display_filters.xsl");
            new MarshalDataServiceImpl().xmlToHtml(srcXML, xsl, outHTMLStream);
        } catch (Throwable e) {
            logger.error("Error: ", e);
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        return outHTMLStream;
    }

    public void getReportExcelXml(Source source, OutputStream result)
            throws TransformerException, IOException, SAXException, ConfigurationException {

        logger.debug("Preparing Excel XML report..");

        try {
            ClassLoader cl = MarshalDataService.class.getClassLoader();
            InputStream xsl = cl.getResourceAsStream("xsl/excel.xsl");
            new MarshalDataServiceImpl().xmlToHtml(source, xsl, result);
        } catch (Throwable e) {
            logger.debug("Excel XML report translation failed!");
            e.printStackTrace();
        }

        logger.debug("Excel XML report ready!");
    }

    public String getXMLFromReport(RetrievedReportXML retrievedReportXML) {
        try {
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            jaxb2Marshaller.marshal(retrievedReportXML, result);
            StringBuffer sb = sw.getBuffer();
            return sb.toString();

        } catch (Throwable ex) {
            System.out.println("Error while marshalling the report: " + ex.getMessage());
        }
        return null;
    }
}
