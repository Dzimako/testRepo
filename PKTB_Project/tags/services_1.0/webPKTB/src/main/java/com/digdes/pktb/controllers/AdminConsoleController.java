package com.digdes.pktb.controllers;

import com.digdes.pktb.controllers.ajaxSupport.JsonView;
import com.digdes.pktb.persistence.beans.ajaxbeans.RailwayDTOMapper;
import com.digdes.pktb.persistence.beans.ajaxbeans.UserGroupDTOMapper;
import com.digdes.pktb.persistence.services.RailwayService;
import com.digdes.pktb.persistence.services.ReportsService;
import com.digdes.pktb.persistence.services.UserGroupService;
import com.digdes.pktb.persistence.services.UserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 28.09.12
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */
public class AdminConsoleController extends MultiActionController {

    RailwayService railwayService;
    UserService userService;
    UserGroupService userGroupService;
    ReportsService reportsService;

    public ModelAndView getAllRailways(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, railwayService.getAll());
    }
    public ModelAndView getAllGroups(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userGroupService.getAll());
    }

    public ModelAndView getUsersByRailwayId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null || request.getParameter("id").length() == 0
                ? null
                : Long.parseLong(request.getParameter("id"));
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userService.getByRailwayId(id));
    }

    public ModelAndView getUsersByGroupId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null || request.getParameter("id").length() == 0
                ? null
                : Long.parseLong(request.getParameter("id"));
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userService.getByGroupId(id));
    }

    public ModelAndView attachUserByCnToRailwayById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cn = request.getParameter("cn");
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        Boolean overwrite = request.getParameter("overwrite") == null;

        // TODO
        String displayName = request.getParameter("displayName");

        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userService.attachUserByCnToRailwayById(cn, id, overwrite, displayName));
    }

    public ModelAndView deleteUserByCn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cn = request.getParameter("cn");
        userService.deleteUserByCn(cn);
        return null;
    }

    public ModelAndView saveOrUpdateRailway(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, railwayService.saveOrUpdate(RailwayDTOMapper.parse(request)));

    }

    public ModelAndView saveOrUpdateGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userGroupService.saveOrUpdate(UserGroupDTOMapper.parse(request)));
    }

    public ModelAndView deleteRailwayById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null || request.getParameter("id").length() == 0
                ? null
                : Long.parseLong(request.getParameter("id"));
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, railwayService.deleteRailwayById(id));
    }

    public ModelAndView deleteGroupById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null || request.getParameter("id").length() == 0
                ? null
                : Long.parseLong(request.getParameter("id"));
        userGroupService.deleteGroupById(id);
        response.getWriter().print("true");
        return null;
    }

    public ModelAndView usersAutocomplete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String search = request.getParameter("q");
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, userService.usersAutocomplete(search));
    }

    public ModelAndView attachUserByCnToGroupById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cn = request.getParameter("cn");
        String displayName = request.getParameter("displayName");
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        userService.attachUserByCnToGroupById(cn, id, displayName);
        response.getWriter().print("{}");
        return null;
    }

    public ModelAndView detachUserByCnFromGroupById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cn = request.getParameter("cn");
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        userService.detachUserByCnFromGroupById(cn,id);
        response.getWriter().print("true");

        return null;
    }

    public ModelAndView moveUserByCnFromGroup1ByIdToGroup2ById (HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cn = request.getParameter("cn");
        Long id1 = request.getParameter("id1") == null
                 ? null
                 : Long.parseLong(request.getParameter("id1"));
        Long id2 = request.getParameter("id2") == null
                ? null
                : Long.parseLong(request.getParameter("id2"));
        userService.moveUserByCnFromGroup1ByIdToGroup2ById(cn,id1, id2);
        response.getWriter().print("true");

        return null;
    }


    public ModelAndView getReportsByParentReportId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.getByParentReportId(id));
    }

    public ModelAndView getAvailibleForUserByCn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cn = request.getParameter("cn");
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.getAvailibleForUserByCn(cn));
    }

    public ModelAndView getAvailibleForGroupById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT, reportsService.getAvailibleForGroupById(id));
    }

    public ModelAndView giveRightsToReportOrFolderByIdToUserByCn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        String cn = request.getParameter("cn");
        reportsService.giveRightsToReportOrFolderByIdToUserByCn(id, cn);
        response.getWriter().print("true");
        return null;
    }

    public ModelAndView takeAwayRightsToReportOrFolderByIdFromUserByCn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = request.getParameter("id") == null
                ? null
                : Long.parseLong(request.getParameter("id"));
        String cn = request.getParameter("cn");
        reportsService.takeAwayRightsToReportOrFolderByIdFromUserByCn(id, cn);
        response.getWriter().print("true");
        return null;
    }

    public ModelAndView giveRightsToReportOrFolderByIdToUserGroupById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long rid = request.getParameter("rid") == null
                 ? null
                 : Long.parseLong(request.getParameter("rid"));
        Long gid = request.getParameter("gid") == null
                 ? null
                 : Long.parseLong(request.getParameter("gid"));

        reportsService.giveRightsToReportOrFolderByIdToUserGroupById(rid, gid);
        response.getWriter().print("true");
        return null;
    }

    public ModelAndView takeAwayRightsToReportOrFolderByIdFromUserGroupById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long rid = request.getParameter("rid") == null
                ? null
                : Long.parseLong(request.getParameter("rid"));
        Long gid = request.getParameter("gid") == null
                ? null
                : Long.parseLong(request.getParameter("gid"));

        reportsService.takeAwayRightsToReportOrFolderByIdFromUserGroupById(rid, gid);
        response.getWriter().print("true");
        return null;
    }

    public RailwayService getRailwayService() {
        return railwayService;
    }

    public void setRailwayService(RailwayService railwayService) {
        this.railwayService = railwayService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserGroupService getUserGroupService() {
        return userGroupService;
    }

    public void setUserGroupService(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    public ReportsService getReportsService() {
        return reportsService;
    }

    public void setReportsService(ReportsService reportsService) {
        this.reportsService = reportsService;
    }
}
