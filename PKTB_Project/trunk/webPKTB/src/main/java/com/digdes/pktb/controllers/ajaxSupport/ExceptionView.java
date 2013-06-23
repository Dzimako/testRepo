package com.digdes.pktb.controllers.ajaxSupport;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * User: Shushkov.R
 * Date: 13.06.2011
 * Time: 19:11:50
 */
public class ExceptionView extends AbstractView {

    private static final String EXCEPTION = "exception";

    @Override
    protected void renderMergedOutputModel(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
         response.setCharacterEncoding("UTF-8");
        OutputStreamWriter out = null;
        /*try {
            out = new OutputStreamWriter(response.getOutputStream(), "UTF8");
            Object exception = map.get(EXCEPTION);
            if (exception instanceof CustomException) {
                response.setContentType("application/json; charset=utf-8");
                response.setStatus(600);
                CustomException customException = (CustomException) exception;
                Object jsonCustomException = JsonSerializer.getJson(customException);
                JSONObject result = new JSONObject();
                result.accumulate(EXCEPTION, jsonCustomException);
                out.write(result.toString());
            } else {
                response.setContentType("plain/text; charset=utf-8");
                response.setStatus(600);
                out.write(exception.toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }*/
    }


}
