package com.digdes.pktb.persistence.dao;

import com.digdes.pktb.persistence.model.Railway;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 28.09.12
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public interface RailwayDao {
    public List<Railway> getAll();

    public Railway getByCode(String code);

    public Railway getById(Long id);

    public void saveOrUpdate(Railway railway);

    public void delete(Railway railway);
}
