package com.digdes.pktb.rolemodel.services;

import com.ibm.portal.ObjectID;
import com.ibm.portal.app.model.ComponentRole;

import java.util.List;

public interface RolesProvider {

    public boolean addPrincipal(String component, ObjectID objectId, String role);

    public List<ComponentRole> getRoles(String component);

    public boolean removePrincipal(String component, ObjectID objectId, String role);

}