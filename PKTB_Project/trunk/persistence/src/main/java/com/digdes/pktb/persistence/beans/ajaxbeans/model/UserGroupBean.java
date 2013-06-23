package com.digdes.pktb.persistence.beans.ajaxbeans.model;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupBean {
    private Long id;
    private String name;

    public UserGroupBean() {
    }

    public UserGroupBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
