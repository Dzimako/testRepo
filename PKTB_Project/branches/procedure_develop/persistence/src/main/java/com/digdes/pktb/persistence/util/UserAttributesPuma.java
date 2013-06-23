package com.digdes.pktb.persistence.util;

public enum UserAttributesPuma {
   //TODO вынести в init spring
    CN("cn"), EMAIL("ibm-primaryEmail"), GIVEN_NAME("givenName"), SN("sn");

    UserAttributesPuma(String value) {
        this.pumaValue = value;
    }

    private String pumaValue;

    public String getValue() {
        return pumaValue;
    }
}