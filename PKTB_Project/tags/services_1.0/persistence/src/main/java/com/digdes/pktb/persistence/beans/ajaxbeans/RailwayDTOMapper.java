package com.digdes.pktb.persistence.beans.ajaxbeans;

import com.digdes.pktb.persistence.beans.ajaxbeans.requests.RailwayDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.model.Railway;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 05.10.12
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class RailwayDTOMapper {
    public static RailwayDTO parse(HttpServletRequest request) {
        RailwayDTO dto = new RailwayDTO();
        if (request.getParameter("id") != null && request.getParameter("id").length() > 0)
            dto.setId(Long.parseLong(request.getParameter("id")));
        dto.setName(request.getParameter("name"));
        dto.setCode(request.getParameter("code"));
        return dto;
    }

    public static RailwayDTO map(Railway railway) {
        RailwayDTO dto = new RailwayDTO();
        dto.setId(railway.getId());
        dto.setName(railway.getName());
        dto.setCode(railway.getCode());

        return dto;
    }
}
