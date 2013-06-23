package com.digdes.pktb.persistence.beans.ajaxbeans.model;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 27.09.12
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
public class RailwayBean {
    private String name;
    private String code;

    public RailwayBean() {
    }

    public RailwayBean(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
