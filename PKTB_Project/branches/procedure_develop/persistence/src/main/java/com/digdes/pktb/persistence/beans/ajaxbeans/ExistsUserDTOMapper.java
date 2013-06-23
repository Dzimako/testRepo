package com.digdes.pktb.persistence.beans.ajaxbeans;

import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ExistsUserDTO;
import com.digdes.pktb.persistence.model.User;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 03.10.12
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class ExistsUserDTOMapper {
    public static ExistsUserDTO map(Boolean exists, User user) {
        ExistsUserDTO dto = new ExistsUserDTO();
        dto.setExists(exists);
        dto.setUser(UserDTOMapper.map(user));
        return dto;
    }
}
