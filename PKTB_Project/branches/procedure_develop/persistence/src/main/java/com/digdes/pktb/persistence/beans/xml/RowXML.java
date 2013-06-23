package com.digdes.pktb.persistence.beans.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * User: Kozlov.D
 * Date: 17.10.12
 * Time: 15:52
 */

@XmlRootElement(name = "Row")
@XmlAccessorType(XmlAccessType.NONE)
public class RowXML {
    private List<String> values;

    public RowXML() {
    }

    public RowXML(List<String> values) {
        this.values = values;
    }

    @XmlElementWrapper(name = "values")
        @XmlElement(name = "string")
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("RowXML");
        sb.append("{values=").append(values);
        sb.append('}');
        return sb.toString();
    }
}
