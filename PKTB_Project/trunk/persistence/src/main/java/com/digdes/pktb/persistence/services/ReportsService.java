package com.digdes.pktb.persistence.services;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.ReportRightsBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetByParentReportIdRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ToggleFavouriteDTO;
import com.digdes.pktb.persistence.model.Report;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:59
 */
public interface ReportsService {

    List<ReportDTO> getByParentReportId(GetByParentReportIdRequest getByParentReportIdRequest);

    List<ReportDTO> getFavouritesByUserCn(String cn);

    Long create(Long parentReportId, String name, Boolean folder, String uid);

    void deleteById(Long id);

    Report getReportById(Long reportId);

    List<String> autocompleteReports(String reportName);

    List<ReportRightsBean> getPermitedUsersAndGroupsOfReport(Long id);

    ReportRightsBean addRights(ReportRightsBean reportRightsBean);

    void removeRights(Long id);

    void setAvailableForAll(Long id);

    ToggleFavouriteDTO toggleReportByIdFavouriteToUserByCn(Long id, String cn);

    List<Object> debug(String query);

    Long createReportIndexes(Long prid, Long orderNum);

    List<ReportDTO> getByParentReportId(Long id);

    List<ReportDTO> getAvailibleForUserByCn(String cn);

    void giveRightsToReportOrFolderByIdToUserByCn(Long id, String cn);

    void takeAwayRightsToReportOrFolderByIdFromUserByCn(Long id, String cn);

    public void giveRightsToReportOrFolderByIdToUserGroupById(Long rid, Long gid);

    public void takeAwayRightsToReportOrFolderByIdFromUserGroupById(Long rid, Long gid);

    List<ReportDTO> getAvailibleForGroupById(Long id);

}
