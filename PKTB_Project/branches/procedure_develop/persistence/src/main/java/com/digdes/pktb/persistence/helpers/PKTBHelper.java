package com.digdes.pktb.persistence.helpers;

import java.io.InputStream;
import java.util.Properties;

public class PKTBHelper {
    public static final String PKTB_INPUT_PARAM_KEY = "in";
    public static final String PKTB_OUTPUT_PARAM_KEY = "out";
    public static final String PKTB_INOUTPUT_PARAM_KEY = "inout";
    public static final String UTF8_BOM = "\uFEFF";

    //Получает значение настроек по ключу
    public static String getSettingsValue(String key) {
        InputStream is = null;
        try {
            is = PKTBHelper.class.getClassLoader().getResourceAsStream("settings.properties");
            Properties properties = new Properties();
            properties.load(is);
            return new String(properties.get(key).toString().getBytes("UTF-8"), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }


    //получает сообщение по ключу
    public static String getMessage(String key) {
        InputStream is = null;
        try {
            is = PKTBHelper.class.getClassLoader().getResourceAsStream("messages.properties");
            Properties properties = new Properties();
            properties.load(is);
            return new String(properties.get(key).toString().getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }
}