package com.digdes.pktb.persistence.model;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 27.09.12
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public interface UserRole {
    public Long getRoleId();

    public void setRoleId(Long roleId);

    public String getRoleKey();

    public void setRoleKey(String roleKey);

    public String getRoleName();

    public void setRoleName(String roleName);

    public String getDescription();

    public void setDescription(String description);
}
