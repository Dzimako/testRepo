package com.digdes.pktb.persistence.services.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.ReportDTOMapper;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.ReportBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.ReportRightsBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserGroupBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetByParentReportIdRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ToggleFavouriteDTO;
import com.digdes.pktb.persistence.dao.ReportDao;
import com.digdes.pktb.persistence.dao.ReportRightsDao;
import com.digdes.pktb.persistence.dao.UserDao;
import com.digdes.pktb.persistence.dao.UserGroupDao;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.ReportRights;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.UserGroup;
import com.digdes.pktb.persistence.model.impl.ReportImpl;
import com.digdes.pktb.persistence.model.impl.ReportRightsImpl;
import com.digdes.pktb.persistence.services.ReportsService;
import com.digdes.pktb.persistence.services.UserService;

import java.util.*;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 14:00
 */
public class ReportServiceImpl implements ReportsService {

    private ReportDao reportDao;
    private ReportRightsDao reportRightsDao;
    private UserDao userDao;
    private UserGroupDao userGroupDao;
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public void setReportRightsDao(ReportRightsDao reportRightsDao) {
        this.reportRightsDao = reportRightsDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserGroupDao(UserGroupDao userGroupDao) {
        this.userGroupDao = userGroupDao;
    }

    public Report getById(Long id) {
        if (id == null)
            return null;
        return reportDao.getById(id);
    }

    public List<ReportDTO> getByParentReportId(GetByParentReportIdRequest getByParentReportIdRequest) {
        Long prid = getByParentReportIdRequest.getId();
        if (prid == 0)
            prid = null;
        Long uid = getByParentReportIdRequest.getUserBean().getUserId();

        List<Report> reports = reportDao.getByParentReportId(prid);
        Collections.sort(reports, new ReportImpl.ReportOrderNumComparator());
        List<ReportDTO> dtos = ReportDTOMapper.map(reports);
        User user = userDao.load(uid);
        Set<ReportRights> reportRightsSet = extractRightsFromUser(user);
        applyReportAvailabilityForTheUser(dtos, reportRightsSet);

        return dtos;
    }

    private Set<ReportRights> extractRightsFromUser(User user) {
        Set<ReportRights> reportRightsSet = user.getReportRights();
        for (UserGroup userGroup: user.getUserGroupSet()){
            reportRightsSet.addAll(userGroup.getReportRights());
        }
        return reportRightsSet;
    }

    public List<ReportDTO> getByParentReportId(Long id) {
        if (id == null)
            return null;
        if (id == 0)
            return  ReportDTOMapper.map(reportDao.getByParentReportId(null));
        return  ReportDTOMapper.map(reportDao.getByParentReportId(id));
    }

    public List<ReportDTO> getAvailibleForUserByCn(String cn) {
        if (cn == null || cn.length() == 0)
            return null;
        User user = userDao.getUserByCN(cn);
        return ReportDTOMapper.mapWithinReportRights(user.getReportRights());
    }

    public void giveRightsToReportOrFolderByIdToUserByCn(Long id, String cn) {
        if (id ==null || cn == null || cn.length() == 0)
            return;
        User user = userDao.getUserByCN(cn);
        if (reportRightsDao.getReportRightsByReportIdAndUserId(id, user.getId()) != null)
            return;
        Report report = reportDao.getById(id);
        ReportRights rights = new ReportRightsImpl();
        rights.setUser(user);
        rights.setReport(report);
        user.getReportRights().add(rights);
        reportRightsDao.saveOrUpdate(rights);
    }

    public void takeAwayRightsToReportOrFolderByIdFromUserByCn(Long id, String cn) {
        if (id == null || cn == null || cn.length() == 0)
            return;
        User user = userDao.getUserByCN(cn);
        Report report = reportDao.getById(id);
        ReportRights rights = reportRightsDao.getReportRightsByReportIdAndUserId(id, user.getId());
        user.getReportRights().remove(rights);
        reportRightsDao.delete(rights);
    }

    public List<ReportDTO> getAvailibleForGroupById(Long id) {
        if (id == null)
            return null;
        UserGroup userGroup = userGroupDao.load(id);
        return ReportDTOMapper.mapWithinReportRights(userGroup.getReportRights());
    }

    public void giveRightsToReportOrFolderByIdToUserGroupById(Long rid, Long gid) {
        if (rid == null || gid == null)
            return;
        if (reportRightsDao.getReportRightsByReportIdAndUserGroupId(rid, gid) != null)
            return;
        UserGroup userGroup = userGroupDao.load(gid);
        Report report = reportDao.getById(rid);
        ReportRights rights = new ReportRightsImpl();
        rights.setUserGroup(userGroup);
        rights.setReport(report);
        userGroup.getReportRights().add(rights);
        reportRightsDao.saveOrUpdate(rights);
    }

    public void takeAwayRightsToReportOrFolderByIdFromUserGroupById(Long rid, Long gid) {
        if (rid == null || gid == null)
            return;

        ReportRights rights = reportRightsDao.getReportRightsByReportIdAndUserGroupId(rid, gid);

        if (rights == null)
            return;

        UserGroup userGroup = userGroupDao.load(gid);
        userGroup.getReportRights().remove(rights);
        reportRightsDao.delete(rights);
    }

    public List<ReportDTO> getFavouritesByUserCn(String cn) {
        User user = userDao.getUserByCN(cn);
        List<Report> reports = user.getFavourites();
        Collections.sort(reports, new ReportImpl.ReportOrderNumComparator());
        List<ReportDTO> dtos = ReportDTOMapper.map(reports);
        Set<ReportRights> reportRightsSet = extractRightsFromUser(user);
        applyReportAvailabilityForTheUser(dtos, reportRightsSet);

        return dtos;
    }

    private void applyReportAvailabilityForTheUser(List<ReportDTO> dtos, Set<ReportRights> reportRightsSet) {
        HashSet<String> resultPathSet = extractFromUserAvailableNodes(reportRightsSet);
        Set<ReportDTO> reportDTOsToRemove = new HashSet<ReportDTO>();
        for(ReportDTO dto: dtos){
            dto.setAvailable(true);
//            HashSet<String> cloneHashSet=(HashSet<String>) resultPathSet.clone();
            HashSet<String> nodeTreePath = new HashSet<String>(Arrays.asList(dto.getTreePath().split(" ")));
            HashSet<String> cloneNodeTreePath = (HashSet<String>) nodeTreePath.clone();

            Boolean reportContains = false;
            for (ReportRights reportRights: reportRightsSet){
                if (nodeTreePath.contains(reportRights.getReport().getId().toString())){
                    reportContains = true;
                    break;
                }
            }
            cloneNodeTreePath.retainAll(resultPathSet);
            if(dto.getShowInTree() != null && !dto.getShowInTree()){
                reportDTOsToRemove.add(dto);
            }
            if (reportContains || cloneNodeTreePath.size() == nodeTreePath.size()){
//                System.out.println("отображаем" + dto.getId());
            } else {
                reportDTOsToRemove.add(dto);
            }
        }
        dtos.removeAll(reportDTOsToRemove);
    }

    private HashSet<String> extractFromUserAvailableNodes(Set<ReportRights> reportRightsSet) {
        HashSet<String> resultPathSet = new HashSet<String>();
        for (ReportRights reportRights: reportRightsSet){
            Set<String> treePathSet = new HashSet<String>(Arrays.asList(reportRights.getReport().getTreePath().split(" ")));
            resultPathSet.addAll(treePathSet);
        }
        return resultPathSet;
    }

//    private void applyReportAvailabilityForTheUser(List<ReportDTO> dtos, User user) {
//        for(ReportDTO dto: dtos)
//            applyReportAvailabilityForTheUser(dto, user);
//    }
//
//    private void applyReportAvailabilityForTheUser(ReportDTO reportDTO, User user) {
//        reportDTO.setAvailable(false);
//
//        if (user.getUserRole() == null)
//            return;
//
//        Set<ReportRights> reportRightsSet = new HashSet<ReportRights>();
//        reportRightsSet  = getReportBranchByReport(reportDao.load(reportDTO.getId()), reportRightsSet);
//
//        if (reportRightsSet.size() == 0)
//            reportDTO.setAvailable(true);
//        else {
//            List<ReportRights> reportRightsList = new ArrayList<ReportRights>(reportRightsSet);
//            System.out.println(Collections.binarySearch(reportRightsList, new ReportRightsImpl(user), new ReportRightsImpl.ReportRightsUserComparator()));
//            if (Collections.binarySearch(reportRightsList, new ReportRightsImpl(user), new ReportRightsImpl.ReportRightsUserComparator()) >= 0){
//                reportDTO.setAvailable(true);
//            } else {
//                for (UserGroup userGroup: user.getUserGroupSet()){
//                    if (Collections.binarySearch(reportRightsList, new ReportRightsImpl(userGroup), new ReportRightsImpl.ReportRightsGroupComparator()) >= 0) {
//                        reportDTO.setAvailable(true);
//                        return;
//                    }
//                }
//            }
//        }
//    }
//
//    private Set<ReportRights> getReportBranchByReport(Report report, Set<ReportRights> reportRightsSet){
//        if (report == null)
//            return reportRightsSet;
//        reportRightsSet.addAll(report.getReportRights());
//        return getReportBranchByReport(report.getParentReport(), reportRightsSet);
//    }

    public Long create(Long parentReportId, String name, Boolean folder, String uid) {
        Report report = new ReportImpl();
        report.setName(name);
        report.setParentReport(getById(parentReportId));
        report.setFolder(folder);
        report.setUid(uid);
        return reportDao.saveOrUpdate(report);
    }

    public void deleteById(Long id) {
        if (id != null)
            reportDao.deleteById(id);
        System.out.println("deleted");
    }

    public Report getReportById(Long reportId) {
        return reportDao.load(reportId);
    }

    public List<String> autocompleteReports(String reportName) {
        List<String> userDisplayNames = new ArrayList<String>();
        List<Report> reports = reportDao.getReportByName(reportName);
        for (Report report : reports) {
            userDisplayNames.add(report.getName());
        }
        return userDisplayNames;
    }

    public List<ReportRightsBean> getPermitedUsersAndGroupsOfReport(Long id) {
        List<ReportRights> reportRightsList = reportRightsDao.getReportRightsByReportId(id);
        List<ReportRightsBean> reportRightsBeans = new ArrayList<ReportRightsBean>();
        for (ReportRights reportRights : reportRightsList) {
            ReportRightsBean reportRightsBean = new ReportRightsBean();
            reportRightsBean.setRightsId(reportRights.getRightsId());
            reportRightsBean.setReport(new ReportBean(reportRights.getReport().getId()));
            if (reportRights.getUser() != null)
                reportRightsBean.setUser(new UserBean(reportRights.getUser().getId(), reportRights.getUser().getDisplayName()));
            if (reportRights.getUserGroup() != null)
                reportRightsBean.setUserGroup(new UserGroupBean(reportRights.getUserGroup().getId(), reportRights.getUserGroup().getName()));
            reportRightsBeans.add(reportRightsBean);
        }
        return reportRightsBeans;
    }

    public ReportRightsBean addRights(ReportRightsBean reportRightsBean) {
        ReportRights reportRights;
        reportRightsBean.setExist(false);

        if (reportRightsBean.getUser() == null && reportRightsBean.getUserGroup() == null)
            return null;

        if (reportRightsBean.getUser() != null)
            reportRights = reportRightsDao.getReportRightsByReportIdAndUserId(reportRightsBean.getReport().getId(), reportRightsBean.getUser().getUserId());
        else
            reportRights = reportRightsDao.getReportRightsByReportIdAndUserGroupId(reportRightsBean.getReport().getId(), reportRightsBean.getUserGroup().getId());
        if (reportRights != null) {
            reportRightsBean.setRightsId(reportRights.getRightsId());
            reportRightsBean.setExist(true);
        } else {
            ReportRights reportRightsToSave;
            if (reportRightsBean.getUser() != null)
                reportRightsToSave = new ReportRightsImpl(userDao.load(reportRightsBean.getUser().getUserId()), reportDao.load(reportRightsBean.getReport().getId()));
            else
                reportRightsToSave = new ReportRightsImpl(reportDao.load(reportRightsBean.getReport().getId()), userGroupDao.load(reportRightsBean.getUserGroup().getId()));
            reportRightsDao.saveOrUpdate(reportRightsToSave);
            reportRightsBean.setRightsId(reportRightsToSave.getRightsId());
        }
        return reportRightsBean;

    }

    public void removeRights(Long id) {
        if (id != null) {
            ReportRights reportRights = reportRightsDao.load(id);
            if (reportRights != null) {
                reportRightsDao.delete(reportRights);
            }
        }
    }

    public void setAvailableForAll(Long id){
        if (id != null) {
            List<ReportRights> reportRightsList = reportRightsDao.loadAllByReportId(id);
            for (ReportRights reportRights: reportRightsList){
                reportRightsDao.delete(reportRights);
            }
        }
    }

    public ToggleFavouriteDTO toggleReportByIdFavouriteToUserByCn(Long id, String cn) {

        if (id == null || cn == null || cn.length() == 0)
            return null;

        User user = userDao.getUserByCN(cn);
        Report report = reportDao.getById(id);

        if (user.getFavourites().indexOf(report) >= 0) {
            System.out.print(">>>>>>>>>>>!!!!!!!!");
            user.getFavourites().remove(report);
            return new ToggleFavouriteDTO(false);

        } else {
            user.getFavourites().add(report);
            return new ToggleFavouriteDTO(true);
        }
    }


    public List<Object> debug(String query) {
        return reportDao.debug(query);
    }

    public Long createReportIndexes(Long prid, Long orderNum){
        List<Report> reports = reportDao.getByParentReportId(prid);
        Collections.sort(reports, new ReportImpl.ReportComparator());
        for(Report report: reports){
            String parentTreePath = report.getParentReport() != null ? report.getParentReport().getTreePath() : "";
            report.setTreePath(report.getId()+" "+parentTreePath.trim());
            report.setOrderNum(orderNum++);
            orderNum = createReportIndexes(report.getId(), orderNum);
        }
        return orderNum;
    }
    public Long createFolder(Long parentId,String name)
    {
        Report report = new ReportImpl();
        report.setFolder(true);
        report.setName(name);
        report.setParentReport(reportDao.getById(parentId));
        List<Report> childReports=reportDao.getByParentReportId(parentId);
        report.setTreePath(reportDao.getById(parentId).getTreePath()+name);
        Collections.sort(childReports,new ReportImpl.ReportOrderNumComparator());
        Iterator itr=childReports.iterator();
        Object lastElement=itr.next();
        while(itr.hasNext())
            lastElement=itr.next();
        Report lastReport= (Report)lastElement;
        report.setOrderNum(lastReport.getOrderNum()+1);
        return reportDao.saveOrUpdate(report);
    }




}
