package com.digdes.pktb.persistence.support;

import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.UserGroup;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 04.10.12
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class UsersAndGroups {
    private Set<User> users;
    private Set<UserGroup> userGroups;

    public UsersAndGroups() {
        this.users = new HashSet<User>();
        this.userGroups = new HashSet<UserGroup>();
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
