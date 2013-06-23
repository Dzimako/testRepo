package com.digdes.pktb.controllers.portletControllers;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.services.UserService;
import com.google.gson.Gson;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * User: Kozlov.D
 * Date: 13.12.2011
 * Time: 17:15:22
 */
public class PKTBPortletController extends AbstractController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        Gson gson = new Gson();
        ModelAndView model = new ModelAndView("mainPage");
        UserBean userBean = userService.getCurrentUser(request,response);
        boolean isAdmin = userBean.getUserRoleBean().getRoleKey().equals("admin");
        String cn = userBean.getCn();
        System.out.print(">>>>>>>>>>>>" + isAdmin);
        model.addObject("currentUser", gson.toJson(userBean));
        model.addObject("isAdmin", isAdmin);
        model.addObject("cn", cn);
        return model;
    }
}
