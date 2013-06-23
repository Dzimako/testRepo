package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.ReportRightsBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */
public class GetPermitedUsersResponse {
    private List<ReportRightsBean> reportRightsBeanList;

    public List<ReportRightsBean> getReportRightsBeanList() {
        return reportRightsBeanList;
    }

    public void setReportRightsBeanList(List<ReportRightsBean> reportRightsBeanList) {
        this.reportRightsBeanList = reportRightsBeanList;
    }
}
