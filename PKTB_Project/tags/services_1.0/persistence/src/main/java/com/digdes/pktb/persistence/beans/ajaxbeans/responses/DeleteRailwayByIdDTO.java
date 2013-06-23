package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

/**
 * Created with IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 16.11.12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class DeleteRailwayByIdDTO {

    boolean usersExist = false;

    public boolean isUsersExist() {
        return usersExist;
    }

    public void setUsersExist(boolean usersExist) {
        this.usersExist = usersExist;
    }
}
