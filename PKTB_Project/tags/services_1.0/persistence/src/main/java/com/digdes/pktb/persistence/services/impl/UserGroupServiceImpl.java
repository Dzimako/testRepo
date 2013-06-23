package com.digdes.pktb.persistence.services.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.UserGroupDTOMapper;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserGroupDTO;
import com.digdes.pktb.persistence.dao.UserDao;
import com.digdes.pktb.persistence.dao.UserGroupDao;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.UserGroup;
import com.digdes.pktb.persistence.model.impl.UserGroupImpl;
import com.digdes.pktb.persistence.services.UserGroupService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 24.10.12
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupServiceImpl implements UserGroupService {
    UserGroupDao userGroupDao;
    UserDao userDao;

    public List<UserGroupDTO> getAll() {
        return UserGroupDTOMapper.map(userGroupDao.getAll());
    }

    public UserGroupDTO saveOrUpdate(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = new UserGroupImpl();

        if (userGroupDTO.getId() != null)
            userGroup = userGroupDao.load(userGroupDTO.getId());

        userGroup.setName(userGroupDTO.getName());

        userGroupDao.saveOrUpdate(userGroup);

        return UserGroupDTOMapper.map(userGroup);
    }

    public void deleteGroupById(Long id) {
        if (id == null)
            return;

        UserGroup userGroup = userGroupDao.load(id);
        if (userGroup != null)
            userGroupDao.delete(userGroup);
    }

    public UserGroupDao getUserGroupDao() {
        return userGroupDao;
    }

    public void setUserGroupDao(UserGroupDao userGroupDao) {
        this.userGroupDao = userGroupDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
