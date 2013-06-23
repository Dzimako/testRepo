package com.digdes.pktb.persistence.beans.wssupport;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 26.09.12
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveReportResult {
    private String result;
    private Long sizeOfResponse;
    private String errorMessage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getSizeOfResponse() {
        return sizeOfResponse;
    }

    public void setSizeOfResponse(Long sizeOfResponse) {
        this.sizeOfResponse = sizeOfResponse;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
