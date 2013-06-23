package com.digdes.pktb.persistence;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * User: Kozlov.D
 * Date: 20.12.2011
 * Time: 16:39:33
 */
public class SpringContextLoaderCustom extends ContextLoaderListener {
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Initialize Context Class Loader Participants");
        super.contextInitialized(event);
    }
}
