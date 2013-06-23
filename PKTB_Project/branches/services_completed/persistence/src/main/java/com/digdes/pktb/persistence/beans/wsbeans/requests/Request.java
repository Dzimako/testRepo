package com.digdes.pktb.persistence.beans.wsbeans.requests;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "request")
public class Request {
    @XmlElementWrapper(name = "params")
    @XmlElement(name = "Pair")
    private List<Pair> params;
    @XmlElement(name="reportName")
    private String reportName;

    public List<Pair> getParams() {
        return params;
    }

    public void setParams(List<Pair> params) {
        this.params = params;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
