package com.digdes.pktb.persistence.dao;

import com.digdes.pktb.persistence.model.ReportRights;
import com.digdes.pktb.persistence.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public interface ReportRightsDao {
    public ReportRights getReportRightsByReportIdAndUserId(Long reportId, Long userId);

    public ReportRights getReportRightsByReportIdAndUserGroupId(Long reportId, Long userGroupId);

    public List<ReportRights> getReportRightsByReportId(Long id);

    public void saveOrUpdate(ReportRights reportRights);

    public ReportRights load(Long id);

    public Boolean isAvailableForAll(Long reportId);

    public Boolean isAvailableForAllCurrentUser(Long reportId, User user);

    public void delete(ReportRights reportRights);

    public List<ReportRights> loadAllByReportId(Long reportId);
}
