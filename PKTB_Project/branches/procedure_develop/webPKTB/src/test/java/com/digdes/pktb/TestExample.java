package com.digdes.pktb;


import com.digdes.pktb.persistence.services.impl.*;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.NumberFormatter;
import java.io.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestExample extends BaseTest {


    @Test
    public void testRequisition() {
       CacheManager cacheManager = CacheManager.getInstance();
        int oneDay = 24 * 60 * 60;
        Cache memoryOnlyCache = new Cache("resultSetCache", 200, false, false, oneDay, oneDay);
        cacheManager.addCache(memoryOnlyCache);
        Cache cache = cacheManager.getCache("resultSetCache");
        Element element = new Element("resultSet", new HashSet<String>());
        cache.put(element);

        Cache cacheTest = cacheManager.getCache("resultSetCache");
        Element resultSetElement = cacheTest.get("resultSet");
        if(resultSetElement.getValue() instanceof HashSet){
            System.out.println("Extracted element is HashSet.");
        }
    }

    @Test
        public void testMathExpressions() {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();

        /*try {
            Calculable calc = new ExpressionBuilder("x * y - 2")
                    .withVariable("x", 1)
                    .withVariable("y", 3)
                    .build();
            System.out.println(calc.calculate());
        } catch (UnknownFunctionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnparsableExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
    }

}