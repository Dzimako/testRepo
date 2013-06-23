package com.digdes.pktb.persistence;

import junit.framework.TestCase;
import org.apache.log4j.Logger;

public class TestExample extends TestCase{
    public void testLogging(){
        Logger log = Logger.getLogger(this.getClass());
        log.info("test logging");
    }
}