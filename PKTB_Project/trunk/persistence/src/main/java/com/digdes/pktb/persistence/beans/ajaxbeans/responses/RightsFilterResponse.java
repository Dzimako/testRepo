package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

import com.digdes.pktb.persistence.model.ReportRights;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class RightsFilterResponse {
    private List<ReportRights> reportRightsList;

    public List<ReportRights> getReportRightsList() {
        return reportRightsList;
    }

    public void setReportRightsList(List<ReportRights> reportRightsList) {
        this.reportRightsList = reportRightsList;
    }
}
