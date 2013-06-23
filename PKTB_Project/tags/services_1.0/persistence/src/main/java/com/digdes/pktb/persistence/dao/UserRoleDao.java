package com.digdes.pktb.persistence.dao;

import com.digdes.pktb.persistence.model.UserRole;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 27.09.12
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public interface UserRoleDao {
    public UserRole load(Long roleId);

    public UserRole loadUserRoleByKey(String key);

    public List<UserRole> listUserRolesByKey(String key);

    public List<UserRole> listAllRoles();
}
