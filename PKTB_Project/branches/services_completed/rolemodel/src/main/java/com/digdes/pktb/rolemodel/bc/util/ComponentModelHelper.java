package com.digdes.pktb.rolemodel.bc.util;

import com.ibm.portal.Localized;
import com.ibm.portal.app.model.ComponentRole;
import com.ibm.portal.app.model.Variable;

/**
 * User: Shushkov.R
 * Date: 02.11.2010
 * Time: 20:26:10
 */
public class ComponentModelHelper {
    // no instances are allowed
    private ComponentModelHelper() {
    }

    public static ComponentRole getComponentRole(String id, String name, String desc) {
        return new RoleImpl(id, name, desc);
    }

    public static Localized getLocalized(String name, String desc) {
        return new LocalizedImpl(name, desc);
    }

    public static Variable getVariable(String name, String title, String desc, Object value) {
        return new VariableImpl(name, title, desc, value);
    }
}
