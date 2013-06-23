package com.digdes.pktb.persistence.beans.ajaxbeans.model;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class ReportBean {
    private Long id;

    public ReportBean() {
    }

    public ReportBean(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
