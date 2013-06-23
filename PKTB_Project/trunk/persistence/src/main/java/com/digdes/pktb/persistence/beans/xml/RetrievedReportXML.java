package com.digdes.pktb.persistence.beans.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * User: Kozlov.D
 * Date: 17.10.12
 * Time: 14:04
 */

@XmlRootElement(name = "retrieveReportReturn")
@XmlAccessorType(XmlAccessType.NONE)
public class RetrievedReportXML {
    private List<ParameterXML> attributes;
    private List<TableXML> tables;

    public RetrievedReportXML() {
    }

    @XmlElementWrapper(name = "attributes")
        @XmlElement(name = "Pair")
    public List<ParameterXML> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ParameterXML> attributes) {
        this.attributes = attributes;
    }

    @XmlElementWrapper(name = "tables")
        @XmlElement(name = "Table")
    public List<TableXML> getTables() {
        return tables;
    }

    public void setTables(List<TableXML> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("RetrievedReportXML");
        sb.append("{attributes=").append(attributes);
        sb.append(", tables=").append(tables);
        sb.append('}');
        return sb.toString();
    }
}
