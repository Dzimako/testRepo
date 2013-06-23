package com.digdes.pktb.persistence.services;

import com.digdes.pktb.persistence.beans.wssupport.Header;
import com.digdes.pktb.persistence.beans.xml.RetrievedReportXML;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * User: Kozlov.D
 * Date: 12.03.2012
 * Time: 14:13:25
 */
public interface MarshalDataService {

    ByteArrayOutputStream getReportHTML(Source srcXML) throws TransformerException, IOException, SAXException, ConfigurationException;

    ByteArrayOutputStream createFiltersHTML(Source srcXML) throws TransformerException, IOException, SAXException, ConfigurationException;

    void getReportExcelXml(Source source, OutputStream result) throws TransformerException, IOException, SAXException, ConfigurationException;

    String getXMLFromReport(RetrievedReportXML retrievedReportXML);

}
