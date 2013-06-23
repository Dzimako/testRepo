package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.dao.ReportStatDao;
import com.digdes.pktb.persistence.model.ReportStat;
import com.digdes.pktb.persistence.model.impl.ReportStatImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 25.09.12
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class ReportStatDaoImpl extends HibernateDaoSupport implements ReportStatDao {
    public void saveReportStat(ReportStat reportStat) {
        getHibernateTemplate().save(reportStat);
    }

    public List<ReportStat> findWithParams(String reportName,
                               Date dateOfDownloadBegin,
                               Date dateOfDownloadEnd,
                               String userDisplayName,
                               Boolean success) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportStatImpl.class);
        if (success != null)
            criteria.add(Restrictions.eq("success", success));
        if (dateOfDownloadBegin != null)
            criteria.add(Restrictions.ge("dateOfDownload", dateOfDownloadBegin));
        if (dateOfDownloadEnd != null)
            criteria.add(Restrictions.le("dateOfDownload", dateOfDownloadEnd));
        if (userDisplayName != null)
            criteria.createCriteria("user").add(Restrictions.ilike("displayName", userDisplayName, MatchMode.ANYWHERE));
        if (reportName != null)
            criteria.createCriteria("report").add(Restrictions.ilike("name", reportName, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }
}
