package com.digdes.pktb.persistence.beans.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: Kozlov.D
 * Date: 17.10.12
 * Time: 15:49
 */

@XmlRootElement(name = "Pair")
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterXML {
    private String name;
    private String value;

    public ParameterXML() {
    }

    public ParameterXML(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("ParameterXML");
        sb.append("{name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
