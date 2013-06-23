package com.digdes.pktb.persistence.services.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.RailwayDTOMapper;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.RailwayDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.DeleteRailwayByIdDTO;
import com.digdes.pktb.persistence.dao.RailwayDao;
import com.digdes.pktb.persistence.dao.UserDao;
import com.digdes.pktb.persistence.model.Railway;
import com.digdes.pktb.persistence.model.impl.RailwayImpl;
import com.digdes.pktb.persistence.services.RailwayService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 28.09.12
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
public class RailwayServiceImpl implements RailwayService {

    RailwayDao railwayDao;
    UserDao userDao;

    public List<Railway> getAll() {
        return railwayDao.getAll();
    }

    public RailwayDTO saveOrUpdate(RailwayDTO dto) {
        Railway railway = new RailwayImpl();
        if (dto.getId() != null)
            railway = railwayDao.getById(dto.getId());

        railway.setCode(dto.getCode());
        railway.setName(dto.getName());
        railwayDao.saveOrUpdate(railway);

        return RailwayDTOMapper.map(railway);
    }

    public DeleteRailwayByIdDTO deleteRailwayById(Long id) {
        if (id == null)
            return new DeleteRailwayByIdDTO();
        if (userDao.getByRailwayId(id).size() == 0){
            Railway railway = railwayDao.getById(id);
            railwayDao.delete(railway);
            return new DeleteRailwayByIdDTO();
        }
        DeleteRailwayByIdDTO dto = new DeleteRailwayByIdDTO();
        dto.setUsersExist(true);
        return dto;

    }

    public RailwayDao getRailwayDao() {
        return railwayDao;
    }

    public void setRailwayDao(RailwayDao railwayDao) {
        this.railwayDao = railwayDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
