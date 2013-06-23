package com.digdes.pktb.persistence.dao;

import com.digdes.pktb.persistence.model.UserGroup;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 03.10.12
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public interface UserGroupDao {
    public List<UserGroup> getUserGroupByName(String name);

    public UserGroup load(Long id);

    public List<UserGroup> getAll();

    public UserGroup getByIdWithUsers(Long id);

    public void saveOrUpdate(UserGroup userGroup);

    public void deleteById(Long id);

    public void delete(UserGroup userGroup);
}
