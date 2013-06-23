package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

import com.digdes.pktb.persistence.model.Railway;
import com.digdes.pktb.persistence.model.UserRole;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 03.10.12
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public class UserDTO {
    private Long id;
    private String cn;
    private String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
