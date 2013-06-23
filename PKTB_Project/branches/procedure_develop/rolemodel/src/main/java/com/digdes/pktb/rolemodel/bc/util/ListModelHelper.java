package com.digdes.pktb.rolemodel.bc.util;

import com.ibm.portal.ListModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * User: Shushkov.R
 * Date: 02.11.2010
 * Time: 20:26:10
 */

public class ListModelHelper {
    // no instances are allowed
    private ListModelHelper() {
    }

    /**
     * Creates a <code>ListModel</code> from a List object.
     *
     * @param aList a list holding the elements of the list model. Can be
     * <code>null</code> - the list models' iterator will return an empty
     * iterator in this case.
     * @return the new list model
     */
    public static final ListModel from(List aList) {
        if (aList == null)
        {
            aList = new ArrayList(0);
        }
        return new ListModelImpl(aList);
    }

    /**
     * A quick implementation of a list model. Takes an existing list as input
     * parameter on construction time.
     */
    private static class ListModelImpl implements ListModel
    {

        private final Collection iList;

        /**
         * Create a list model from the given list.
         *
         * @param aCollection the contents of the list model
         */
        public ListModelImpl(Collection aCollection) {
            iList = aCollection;
        }

        // non-javadoc: see interface
        public Iterator iterator() {
            return iList.iterator();
        }
    }
}
