package com.digdes.pktb.controllers;

import com.digdes.pktb.controllers.ajaxSupport.JsonView;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.ReportRightsBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetByParentReportIdRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetFilterDictionaryRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GetReportRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.requests.GlobalStatisticsRequest;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.GetReportResponse;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ReportDTO;
import com.digdes.pktb.persistence.dao.impl.RZDReportDaoImpl;
import com.digdes.pktb.persistence.helpers.PKTBHelper;
import com.digdes.pktb.persistence.services.ReportStatService;
import com.digdes.pktb.persistence.services.ReportsService;
import com.digdes.pktb.persistence.services.RZDReportsService;
import com.digdes.pktb.persistence.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 13:04
 */
public class ReportsController extends MultiActionController {

    ReportsService reportsService;
    ReportStatService reportStatService;
    RZDReportsService rzdReportsService;
    UserService userService;

    public void setReportsService(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    public void setRzdReportsService(RZDReportsService rzdReportsService) {
        this.rzdReportsService = rzdReportsService;
    }

    public void setReportStatService(ReportStatService reportStatService) {
        this.reportStatService = reportStatService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /*public ModelAndView getRequisitionsOfCommisionMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Long commissionId = null;
        BranchBean branchBean = gson.fromJson(request.getParameter("branchBean"), BranchBean.class);
        if(request.getParameter("commission_id") != null){
            commissionId = Long.parseLong(request.getParameter("commission_id"));
        } else {
            AppUserBean currentUser = userService.getCurrentUser(request, response);
            if(currentUser.getCommissionBeans() != null){
                for(CommissionBean commissionBean : currentUser.getCommissionBeans()){
                    if(commissionBean.getBranch().getBranch_id().equals(branchBean.getBranch_id())){
                        commissionId = commissionBean.getCommision_id();
                    }
                }
            }
        }
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, commisionService.getRequisitionsOfCommisionMember(commissionId).toArray());
    }*/

    public ModelAndView getByParentReportId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        GetByParentReportIdRequest getByParentReportIdRequest = gson.fromJson(request.getParameter("getByParentReportIdRequest"), GetByParentReportIdRequest.class);
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.getByParentReportId(getByParentReportIdRequest));
    }

    public ModelAndView getReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        UserBean userBean = new UserBean();
        userBean.setUserId((long) 2);*/
        Gson gson = new Gson();
        GetReportRequest getReportRequest = gson.fromJson(request.getParameter("requestBean"), GetReportRequest.class);
        System.out.println("Get report request: " + getReportRequest);
        GetReportResponse reportContent = rzdReportsService.getReport(getReportRequest.getUserBean(), getReportRequest.getReportId(), getReportRequest.getPairList());



        ModelAndView modelAndView = new ModelAndView("bodyView");
//        System.out.println("reportContent: " + reportContent);
        modelAndView.addObject("reportContent", gson.toJson(reportContent));
//        if (reportContent.getSuccess()){
//            System.out.println("response error: "+response.SC_INTERNAL_SERVER_ERROR);
//            response.sendError(response.SC_INTERNAL_SERVER_ERROR);
//        }
        return modelAndView;



    }

    public ModelAndView getReportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Gson gson = new Gson();
        GetReportRequest getReportRequest = gson.fromJson(request.getParameter("requestBean"), GetReportRequest.class);
        System.out.println("Get report request: " + getReportRequest);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "inline; filename=\"report.xls\"");

        GetReportResponse reportContent = rzdReportsService.getReportExcelXml(getReportRequest.getUserBean(), getReportRequest.getReportId(), getReportRequest.getPairList());
        response.getWriter().print(PKTBHelper.UTF8_BOM + reportContent.getResponse());
        /*
        rzdReportsService.getReportExcelXml(
                getReportRequest.getUserBean(),
                getReportRequest.getReportId(),
                getReportRequest.getPairList(),
                response.getOutputStream());
        System.out.print("<<<<<<<<<<<<<<<<");
        */

        return null;
    }

    public ModelAndView showById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        ModelAndView modelAndView = new ModelAndView("reportView");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    public ModelAndView filterById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        ModelAndView modelAndView = new ModelAndView("filterView");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        if (name == null) return null;

        Long parentReportId = request.getParameter("parentReportId") == null
                ? null
                : Long.parseLong(request.getParameter("parentReportId"));
        Boolean folder = request.getParameter("folder") == null
                ? false
                : Boolean.parseBoolean(request.getParameter("folder"));
        String uid = request.getParameter("uid");

        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.create(parentReportId, name, folder, uid));
    }

    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));

        reportsService.deleteById(id);
        return null;
    }

    public ModelAndView getFiltersHTML(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        ReportDTO requestBean = gson.fromJson(request.getParameter("report"), ReportDTO.class);
        ModelAndView modelAndView = new ModelAndView("filterView");
        modelAndView.addObject("id", requestBean.getId());
        modelAndView.addObject("reportFilters", rzdReportsService.getFiltersHTML(requestBean));
        return modelAndView;
    }

    /*UserBean userBean = new UserBean();
            userBean.setUserId((long) 2);*/

    public ModelAndView getFilterDictionary(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
//        UserBean userBean = new UserBean();
//        userBean.setUserId((long) 2);
        GetFilterDictionaryRequest requestBean = gson.fromJson(request.getParameter("requestBean"), GetFilterDictionaryRequest.class);
//        requestBean.setUserBean(userBean);
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, rzdReportsService.getDictionary(requestBean.getUserBean(), requestBean.getFilterUID()));
    }

    public ModelAndView statistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));

//        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportStatService.getReportStatByReportId(id));
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportStatService.getReportStatByReportId(id));
    }

    public ModelAndView globalStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm").create();
        GlobalStatisticsRequest globalStatisticsRequest = gson.fromJson(request.getParameter("globalStatisticsRequest"), GlobalStatisticsRequest.class);
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportStatService.getGlobalReportStat(globalStatisticsRequest));
    }

    public ModelAndView autocompleteReports(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reportName = request.getParameter("term") == null
                ? null
                : request.getParameter("term");
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.autocompleteReports(reportName));
    }

    public ModelAndView autocompleteUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("term") == null
                ? null
                : request.getParameter("term");
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userService.autocompleteUsers(userName));
    }

    public ModelAndView autocompleteUserBeans(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userOrGroupName = request.getParameter("term") == null
                ? null
                : request.getParameter("term");
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userService.autocompleteUserAndGroups(userOrGroupName));
    }

    public ModelAndView rightsFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        ModelAndView modelAndView = new ModelAndView("rightsFilter");
        Gson gson = new Gson();
        modelAndView.addObject("reportRightsBeans", gson.toJson(reportsService.getPermitedUsersAndGroupsOfReport(id)));
        return modelAndView;
    }

    public ModelAndView addRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        ReportRightsBean reportRightsBean = gson.fromJson(request.getParameter("reportRightsBean"), ReportRightsBean.class);
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.addRights(reportRightsBean));
    }

    public ModelAndView removeRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        reportsService.removeRights(id);
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, null);
    }

    public ModelAndView setAvailableForAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        reportsService.setAvailableForAll(id);
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, null);
    }

    public ModelAndView debug(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.debug(request.getParameter("q")));
    }

    public ModelAndView getReportFromDB(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        GetReportRequest getReportRequest = gson.fromJson(request.getParameter("requestBean"), GetReportRequest.class);
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, rzdReportsService.getReportFromDB( getReportRequest)); //getReportRequest.getDatasource(), getReportRequest.getProc_name(), getReportRequest.getPairList(), getReportRequest.getReportId(),
    }

    public ModelAndView getFavourites(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cn = request.getParameter("cn");
        if (cn == null)
            return null;
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.getFavouritesByUserCn(cn));
    }


    public ModelAndView toggleReportByIdFavouriteToUserByCn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        String cn = request.getParameter("cn");
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.toggleReportByIdFavouriteToUserByCn(id, cn));
    }

    public ModelAndView addReportByIdToUserByCnFavourites(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    public ModelAndView deleteReportByIdFromUserByCnFavourites(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}