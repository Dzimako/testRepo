package com.digdes.pktb.persistence.beans.wsbeans.requests;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "getDictionaryWithParams", namespace = "http://ws.rzd.epam.com")
@XmlType(propOrder={"consumer","uid","params"})
public class DictionaryRequest {
    @XmlElement(name="consumer")
    private ConsumerBean consumer;
    @XmlElement(name="uid")
    private String uid;
    @XmlElementWrapper(name = "params")
        @XmlElement(name = "Pair")
    private List<Pair> params;

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

    public List<Pair> getParams() {
        return params;
    }

    public void setParams(List<Pair> params) {
        this.params = params;
    }
}
