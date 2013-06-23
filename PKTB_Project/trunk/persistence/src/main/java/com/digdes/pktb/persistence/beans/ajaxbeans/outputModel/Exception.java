package com.digdes.pktb.persistence.beans.ajaxbeans.outputModel;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 21.11.12
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public class Exception {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Exception");
        sb.append("{key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
