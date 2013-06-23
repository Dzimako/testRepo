package com.digdes.pktb.persistence.dao;

import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserDTO;
import com.digdes.pktb.persistence.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 25.09.12
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
    public User load(Long userId);

    public User getUserByCN(String userCN);

    public List<User> getUserByDisplayName(String displayName);

    public List<User> getByRailwayId(Long id);

    public void saveOrUpdate(User user);

    public void delete(User user);

    public List<User> search(String search);
}
