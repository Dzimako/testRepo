package com.digdes.pktb.persistence.beans.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * User: Kozlov.D
 * Date: 17.10.12
 * Time: 15:51
 */

@XmlRootElement(name = "Table")
@XmlAccessorType(XmlAccessType.NONE)
public class TableXML {
    private List<RowXML> rows;

    public TableXML() {
    }

    @XmlElementWrapper(name = "rows")
        @XmlElement(name = "Row")
    public List<RowXML> getRows() {
        return rows;
    }

    public void setRows(List<RowXML> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("TableXML");
        sb.append("{rows=").append(rows);
        sb.append('}');
        return sb.toString();
    }
}
