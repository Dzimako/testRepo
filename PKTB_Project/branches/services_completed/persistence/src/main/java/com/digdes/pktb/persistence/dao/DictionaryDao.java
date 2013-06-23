package com.digdes.pktb.persistence.dao;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 21.11.12
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public interface DictionaryDao {
    public Map<String, String> executeDictionaryRequest(String datasource, String keyCell, String valueCell, String tableName);
}
