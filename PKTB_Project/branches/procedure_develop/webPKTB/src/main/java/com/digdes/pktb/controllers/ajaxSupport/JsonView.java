package com.digdes.pktb.controllers.ajaxSupport;

import com.digdes.pktb.persistence.model.Report;
import com.google.gson.*;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Map;

/** ---------------------------------------------------------------------------
 * User: Shushkov.R
 * Date: 01.05.2011
 * Time: 0:33:34
 * ----------------------------------------------------------------------------
 */
public class JsonView extends AbstractView {

    public static String JSON_OBJECT = "JSON";
    public static String VIEW_JSON = "jsonView";
   

    protected void renderMergedOutputModel(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
       response.setContentType("application/json; charset=utf-8");
       PrintWriter out = null;
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd.MM.yyyy HH:mm:ss")
                   /* .registerTypeAdapter(Report.class, new JsonSerializer<Report>(){
                        @Override
                        public JsonElement serialize(Report report, Type type, JsonSerializationContext jsonSerializationContext) {
                            JsonObject jsonObject = new JsonObject();
                            //System.out.print("id=" + report.getId() + ",");
                            jsonObject.addProperty("id", report.getId());
                            //System.out.print("name=" + report.getName() + ";");
                            jsonObject.addProperty("name", report.getName());
                            jsonObject.addProperty("folder", report.getFolder());
                            jsonObject.addProperty("uid", report.getUid());
                            return jsonObject;
                        }
                    })    */
                    .create();
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