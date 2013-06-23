package com.digdes.pktb.persistence;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * -------------------------------------------------------
 * Created by IntelliJ IDEA.
 * User: Shushkov.R
 * Date: 05.09.11
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 * --------------------------------------------------------
 */
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static ClassLoader originalClassloader;    //For Business Component

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        System.out.println("==========================================================================================");
        System.out.println("");
        System.out.println("****************************** STARTING PKTB *****************************");
        System.out.println("");
        System.out.println("==========================================================================================");
        applicationContext = context;
        originalClassloader = Thread.currentThread().getContextClassLoader();
        InputStream is = null;
        Properties properties = new Properties();
        try {
            is = SpringContext.class.getClassLoader().getResourceAsStream("settings.properties");
            properties.load(is);
            String newVersion = Long.toHexString(System.nanoTime());
            properties.setProperty("bc.version", newVersion);
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
        try {
            properties.store(new FileOutputStream(SpringContext.class.getClassLoader().getResource("settings.properties").getFile()), "");
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ClassLoader getOriginalClassLoader() {
        return originalClassloader;
    }

    public static void setOriginalClassLoader(ClassLoader classloader) {
        SpringContext.originalClassloader = classloader;
    }
}
