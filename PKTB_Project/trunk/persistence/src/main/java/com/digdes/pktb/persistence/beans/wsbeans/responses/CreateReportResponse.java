package com.digdes.pktb.persistence.beans.wsbeans.responses;

import javax.xml.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "createReportResponse", namespace = "http://ws.rzd.epam.com")
public class CreateReportResponse {
    @XmlElement(name="createReportReturn")
    private String createReportReturn;

    public String getCreateReportReturn() {
        return createReportReturn;
    }

    public void setCreateReportReturn(String createReportReturn) {
        this.createReportReturn = createReportReturn;
    }
}
