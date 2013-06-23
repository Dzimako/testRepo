package com.digdes.pktb.persistence.model;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:44
 */
public interface Railway {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getCode();

    void setCode(String code);
}
