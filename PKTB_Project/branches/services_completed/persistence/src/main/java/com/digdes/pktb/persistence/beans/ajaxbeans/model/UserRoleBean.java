package com.digdes.pktb.persistence.beans.ajaxbeans.model;

import com.digdes.pktb.persistence.model.Railway;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 24.09.12
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleBean {
    private Long roleId;
    private String roleKey;
    private String roleName;

    public UserRoleBean() {
    }

    public UserRoleBean(String roleKey, String roleName) {
        this.roleKey = roleKey;
        this.roleName = roleName;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserRoleBean");
        sb.append("{roleId=").append(roleId);
        sb.append(", roleKey='").append(roleKey).append('\'');
        sb.append(", roleName='").append(roleName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
