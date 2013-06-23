package com.digdes.pktb.persistence.model.impl;

import com.digdes.pktb.persistence.model.UserRole;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 27.09.12
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleImpl implements UserRole {
    private Long roleId;
    private String roleKey;
    private String roleName;
    private String description;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleImpl role = (UserRoleImpl) o;

        if (!roleKey.equals(role.roleKey)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return roleKey.hashCode();
    }
}
