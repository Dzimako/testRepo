package com.digdes.pktb.persistence.beans.wsbeans.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Pair")
public class Pair {
    @XmlElement(name="name")
    private String name;
    @XmlElement(name="value")
    private String value;
    @XmlElement(name="inputParam")
    private String inputParam;
    @XmlElement(name="orderNum")
    private Integer orderNum;
    @XmlElement(name="paramDataType")
    private Integer paramDataType;
    @XmlElement(name="markerSQLCode")
    private Boolean markerSQLCode;

    public Pair() {
    }

    public Pair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Pair(String name, String value, String inputParam, Integer orderNum, Integer paramDataType, Boolean markerSQLCode) {
        this.name = name;
        this.value = value;
        this.inputParam = inputParam;
        this.orderNum = orderNum;
        this.paramDataType = paramDataType;
        this.markerSQLCode = markerSQLCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInputParam() {
        return inputParam;
    }

    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getParamDataType() {
        return paramDataType;
    }

    public void setParamDataType(Integer paramDataType) {
        this.paramDataType = paramDataType;
    }

    public Boolean getMarkerSQLCode() {
        return markerSQLCode;
    }

    public void setMarkerSQLCode(Boolean markerSQLCode) {
        this.markerSQLCode = markerSQLCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Pair");
        sb.append("{name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", inputParam='").append(inputParam).append('\'');
        sb.append(", orderNum=").append(orderNum);
        sb.append(", paramDataType=").append(paramDataType);
        sb.append('}');
        return sb.toString();
    }


}
