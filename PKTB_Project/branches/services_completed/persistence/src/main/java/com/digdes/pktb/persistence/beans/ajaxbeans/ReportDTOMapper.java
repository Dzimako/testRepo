package com.digdes.pktb.persistence.beans.ajaxbeans;

import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.ReportRights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 26.09.12
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class ReportDTOMapper {
    public static ReportDTO map(Report report) {
        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setFolder(report.getFolder());
        dto.setName(report.getName());
        dto.setTreePath(report.getTreePath());
        dto.setShowInTree(report.getShowInTree());
        dto.setFullName(report.getFullName());

        return dto;
    }
    public static List<ReportDTO> map(List<Report> reports){
        List<ReportDTO> dtos = new ArrayList<ReportDTO>(reports.size());
        for(Report report : reports) {
            dtos.add(map(report));
        }
        return dtos;
    }
    public static List<ReportDTO> mapWithinReportRights(Collection<ReportRights> rights){
        List<ReportDTO> dtos = new ArrayList<ReportDTO>(rights.size());
        for(ReportRights right : rights) {
            dtos.add(map(right.getReport()));
        }
        return dtos;
    }
}
