package com.digdes.pktb.rolemodel.bc.util;

import com.ibm.portal.ListModel;
import com.ibm.portal.Localized;

import java.util.ArrayList;
import java.util.Locale;

/**
 * User: Shushkov.R
 * Date: 02.11.2010
 * Time: 20:26:10
 */

public class LocalizedImpl implements Localized{
    private String name = null;
    private String desc = null;

    public LocalizedImpl(String name, String desc) {
        this.name = name;
        this.desc = desc;
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

