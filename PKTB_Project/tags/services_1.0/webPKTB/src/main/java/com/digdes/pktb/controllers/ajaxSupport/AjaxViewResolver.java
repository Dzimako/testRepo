package com.digdes.pktb.controllers.ajaxSupport;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import java.util.Locale;

/**
 * User: Shushkov.R
 * Date: 01.05.2011
 * Time: 0:30:49
 */
public class AjaxViewResolver extends AbstractCachingViewResolver {

    private View ajaxView;

    protected View loadView(String viewName, Locale locale) throws Exception {

        View view = (View) getApplicationContext().getBean(viewName);
        if(view!=null)  ajaxView = view;
        return getAjaxView();
    }

    public View getAjaxView() {
        return ajaxView;
    }

    public void setAjaxView(View ajaxView) {
        this.ajaxView = ajaxView;
    }


}
