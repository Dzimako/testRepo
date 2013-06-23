package com.digdes.pktb;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.test.ConditionalTestCase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User: Serdyuk.A
 * Date: Apr 19, 2012
 * Time: 6:47:44 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:daoContext.xml", "classpath*:reportsController_context.xml"})

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true )
@Transactional
public abstract class  BaseTest extends ConditionalTestCase {

    @Resource
    protected SessionFactory sessionFactory;

}
