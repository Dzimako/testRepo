package com.digdes.pktb.controllers.ajaxSupport;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * User: Shushkov.R
 * Date: 19.06.2011
 * Time: 4:21:03
 */
public class HTMLView extends AbstractView {
    public static String VIEW_NAME = "htmlView";
    public static String CONTENT = "CONTENT";

    protected void renderMergedOutputModel(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
       response.setContentType("text/html; charset=utf-8");

       PrintWriter out = null;
        try {
            out = response.getWriter();
            if (map.get(CONTENT) != null) {
                out.print(map.get(CONTENT));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
