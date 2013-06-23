package com.digdes.pktb.persistence.beans.wssupport;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 26.09.12
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class CreateReportResult {
    private String result;
    private Long sizeOfRequest;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getSizeOfRequest() {
        return sizeOfRequest;
    }

    public void setSizeOfRequest(Long sizeOfRequest) {
        this.sizeOfRequest = sizeOfRequest;
    }
}
