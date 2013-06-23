package com.digdes.pktb.persistence.services;

import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserGroupDTO;
import com.digdes.pktb.persistence.model.UserGroup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 24.10.12
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public interface UserGroupService {
    public List<UserGroupDTO> getAll();
    UserGroupDTO saveOrUpdate(UserGroupDTO userGroupDTO);
    public void deleteGroupById(Long id);
}
