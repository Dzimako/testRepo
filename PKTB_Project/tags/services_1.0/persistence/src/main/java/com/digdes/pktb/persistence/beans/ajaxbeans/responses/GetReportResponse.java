package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 09.10.12
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class GetReportResponse {
    private String response;
    private Boolean success;

    public GetReportResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
