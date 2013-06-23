package com.digdes.pktb.controllers;

import com.digdes.pktb.controllers.ajaxSupport.JsonView;
import com.digdes.pktb.persistence.services.UserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Kozlov.D
 * Date: 26.01.2012
 * Time: 1:08:56
 */
public class UserController extends MultiActionController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView findUserByDisplayName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(JsonView.VIEW_JSON, JsonView.JSON_OBJECT,"");
    }
}
