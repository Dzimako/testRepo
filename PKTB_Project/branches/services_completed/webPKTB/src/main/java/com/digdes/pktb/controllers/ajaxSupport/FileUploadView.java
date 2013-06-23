package com.digdes.pktb.controllers.ajaxSupport;

import com.google.gson.Gson;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * User: Shushkov.R
 * Date: 24.06.2011
 * Time: 2:44:51
 */
public class FileUploadView extends AbstractView {

    public static String JSON_OBJECT = "JSON";
   public static String VIEW_NAME = "fileUploadView";

    protected void renderMergedOutputModel(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {

      PrintWriter out = null;
        try {
            Gson gson = new Gson();
            out = response.getWriter();
            if (map.get(JSON_OBJECT) != null) {
                out.print(gson.toJson(map.get(JSON_OBJECT)));
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

