package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.ReportLevel;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:43
 */
public class ReportLevelImpl implements ReportLevel{

    private Long id;
    private String name;
    private String key;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
