package com.digdes.pktb.rolemodel.bc.util;

import com.ibm.portal.app.model.ComponentRole;

/**
 * User: Shushkov.R
 * Date: 02.11.2010
 * Time: 20:26:10
 */
public class RoleImpl extends LocalizedImpl implements ComponentRole{

    private String id = null;

    public RoleImpl(String id, String name, String desc) {
        super(name, desc);
        this.id = id;
    }

    public String getRoleId() {
        return id;
    }
}