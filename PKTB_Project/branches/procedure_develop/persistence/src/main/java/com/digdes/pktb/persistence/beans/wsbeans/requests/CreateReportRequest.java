package com.digdes.pktb.persistence.beans.wsbeans.requests;

import javax.xml.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "createReport", namespace = "http://ws.rzd.epam.com")
@XmlType(propOrder={"consumer","request"})
public class CreateReportRequest {
    @XmlElement(name="consumer")
    private ConsumerBean consumer;
    @XmlElement(name="request")
    private Request request;

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
