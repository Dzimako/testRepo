package com.digdes.pktb.persistence.beans.ajaxbeans.requests;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 05.10.12
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class RailwayDTO {

    Long id;
    String name;
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
