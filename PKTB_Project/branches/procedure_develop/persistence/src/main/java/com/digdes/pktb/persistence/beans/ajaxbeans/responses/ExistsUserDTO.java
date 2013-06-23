package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 03.10.12
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public class ExistsUserDTO {

    Boolean exists;
    UserDTO user;

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
