package com.digdes.pktb.persistence.beans.wsbeans.responses;

import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "getDictionaryResponse", namespace = "http://ws.rzd.epam.com")
public class DictionaryResponse {
    @XmlElementWrapper(name = "getDictionaryReturn")
    @XmlElement(name = "Pair")
    private List<Pair> pairList;

    public List<Pair> getPairList() {
        return pairList;
    }

    public void setPairList(List<Pair> pairList) {
        this.pairList = pairList;
    }
}
