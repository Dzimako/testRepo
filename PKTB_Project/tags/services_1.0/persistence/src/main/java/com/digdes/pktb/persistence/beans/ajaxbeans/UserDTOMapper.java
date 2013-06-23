package com.digdes.pktb.persistence.beans.ajaxbeans;

import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserDTO;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 03.10.12
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class UserDTOMapper {
    public static UserDTO map(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setCn(user.getCn());
        dto.setDisplayName(user.getDisplayName());

        return dto;
    }

    public static List<UserDTO> map(Collection<User> users){
        if (users == null)
            return new ArrayList<UserDTO>(0);
        List<UserDTO> dtos = new ArrayList<UserDTO>(users.size());
        for(User user : users) {
            dtos.add(map(user));
        }
        return dtos;
    }
}
