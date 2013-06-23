package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 09.10.12
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class GetReportResponse {
    private String reportName;
    private String response;
    private Boolean success;
    private String secondsElapsed;
    private String estimatedMarshalingTime;
    private Double enterControllerTime;
    private Double exitControllerTime;

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

    public String getSecondsElapsed() {
        return secondsElapsed;
    }

    public void setSecondsElapsed(String secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }

    public String getEstimatedMarshalingTime() {
        return estimatedMarshalingTime;
    }

    public void setEstimatedMarshalingTime(String estimatedMarshalingTime) {
        this.estimatedMarshalingTime = estimatedMarshalingTime;
    }

    public Double getEnterControllerTime() {
        return enterControllerTime;
    }

    public void setEnterControllerTime(Double enterControllerTime) {
        this.enterControllerTime = enterControllerTime;
    }

    public Double getExitControllerTime() {
        return exitControllerTime;
    }

    public void setExitControllerTime(Double exitControllerTime) {
        this.exitControllerTime = exitControllerTime;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
