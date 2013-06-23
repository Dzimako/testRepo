package com.digdes.pktb.persistence.beans.ajaxbeans;

import com.digdes.pktb.persistence.beans.ajaxbeans.requests.RailwayDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserGroupDTO;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.UserGroup;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 02.11.12
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupDTOMapper {
    public static UserGroupDTO map(UserGroup userGroup) {
        UserGroupDTO dto = new UserGroupDTO();
        dto.setId(userGroup.getId());
        dto.setName(userGroup.getName());

        return dto;
    }

    public static List<UserGroupDTO> map(List<UserGroup> userGroups){
        if (userGroups == null)
            return new ArrayList<UserGroupDTO>(0);
        List<UserGroupDTO> dtos = new ArrayList<UserGroupDTO>(userGroups.size());
        for(UserGroup userGroup : userGroups) {
            dtos.add(map(userGroup));
        }
        return dtos;
    }

    public static UserGroupDTO parse(HttpServletRequest request) {
        UserGroupDTO dto = new UserGroupDTO();
        dto.setName(request.getParameter("name"));
        try {
            dto.setId(Long.parseLong(request.getParameter("id")));
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {}

        return dto;
    }
}
