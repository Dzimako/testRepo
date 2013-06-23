package com.digdes.pktb.rolemodel.bc.util;

import com.ibm.portal.ListModel;
import com.ibm.portal.app.model.Variable;

import java.util.ArrayList;
import java.util.Locale;

/**
 * User: Shushkov.R
 * Date: 02.11.2010
 * Time: 20:26:10
 */

public class VariableImpl implements Variable{

    private String title = null;
    private String name = null;
    private String desc = null;
    private Object value = "default";

    public VariableImpl(String name, String title, String desc, Object value) {
        this.title = title;
        this.name = name;
        this.desc = desc;
        if (value != null)
        {
            this.value = value;
        }
    }

    public String getVariableName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return Variable.STRING;
    }

    public String getTitle(Locale aLocale) {
        return name;
    }

    public String getDescription(Locale aLocale) {
        return desc;
    }

    public ListModel getLocales() {
        ArrayList list = new ArrayList();
        list.add(Locale.ENGLISH);
        return ListModelHelper.from(list);
    }
}