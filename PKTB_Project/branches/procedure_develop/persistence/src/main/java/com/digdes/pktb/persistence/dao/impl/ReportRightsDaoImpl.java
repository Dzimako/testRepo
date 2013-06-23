package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.dao.ReportRightsDao;
import com.digdes.pktb.persistence.model.ReportRights;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.impl.ReportRightsImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 02.10.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class ReportRightsDaoImpl extends HibernateDaoSupport implements ReportRightsDao {
    public List<ReportRights> getReportRightsByReportId(Long id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportRightsImpl.class);
        criteria.createCriteria("report").add(Restrictions.eq("id", id));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public ReportRights getReportRightsByReportIdAndUserId(Long reportId, Long userId){
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportRightsImpl.class);
        criteria.createCriteria("report").add(Restrictions.eq("id", reportId));
        criteria.createCriteria("user").add(Restrictions.eq("id", userId));
        if (getHibernateTemplate().findByCriteria(criteria).size() > 0)
            return (ReportRights) getHibernateTemplate().findByCriteria(criteria).get(0);
        return null;
    }

    public ReportRights getReportRightsByReportIdAndUserGroupId(Long reportId, Long userGroupId){
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportRightsImpl.class);
        criteria.createCriteria("report").add(Restrictions.eq("id", reportId));
        criteria.createCriteria("userGroup").add(Restrictions.eq("id", userGroupId));
        if (getHibernateTemplate().findByCriteria(criteria).size() > 0)
            return (ReportRights) getHibernateTemplate().findByCriteria(criteria).get(0);
        return null;
    }

    public void saveOrUpdate(ReportRights reportRights){
        getHibernateTemplate().saveOrUpdate(reportRights);
    }

    public ReportRights load(Long id){
        return (ReportRights) getHibernateTemplate().load(ReportRightsImpl.class, id);
    }

    public Boolean isAvailableForAll(Long reportId){
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportRightsImpl.class);
        criteria.createCriteria("report").add(Restrictions.eq("id", reportId));
        return getHibernateTemplate().findByCriteria(criteria).size() == 0;
    }

    public Boolean isAvailableForAllCurrentUser(Long reportId, User user){
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportRightsImpl.class);
        criteria.createCriteria("report").add(Restrictions.eq("id", reportId));
        criteria.createCriteria("user").add(Restrictions.eq("id", user.getId()));
        return getHibernateTemplate().findByCriteria(criteria).size() > 0;
    }

    public void delete(ReportRights reportRights){
        getHibernateTemplate().delete(reportRights);
    }

    public List<ReportRights> loadAllByReportId(Long reportId){
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportRightsImpl.class);
        criteria.createCriteria("report").add(Restrictions.eq("id", reportId));
        return getHibernateTemplate().findByCriteria(criteria);
    }
}
