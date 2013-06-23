package com.digdes.pktb.persistence.services;

import com.digdes.pktb.persistence.beans.ajaxbeans.requests.RailwayDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.DeleteRailwayByIdDTO;
import com.digdes.pktb.persistence.model.Railway;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 28.09.12
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public interface RailwayService {
    public List<Railway> getAll();
    public RailwayDTO saveOrUpdate(RailwayDTO dto);
    public DeleteRailwayByIdDTO deleteRailwayById(Long id);
}
