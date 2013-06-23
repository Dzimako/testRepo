package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.Railway;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:44
 */
public class RailwayImpl implements Railway{

    private Long id;
    private String name;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
