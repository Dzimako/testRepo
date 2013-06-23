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
@XmlRootElement(name = "getDictionary", namespace = "http://ws.rzd.epam.com")
@XmlType(propOrder={"consumer","uid"})
public class DictionaryRequest {
    @XmlElement(name="consumer")
    private ConsumerBean consumer;
    @XmlElement(name="uid")
    private String uid;

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
