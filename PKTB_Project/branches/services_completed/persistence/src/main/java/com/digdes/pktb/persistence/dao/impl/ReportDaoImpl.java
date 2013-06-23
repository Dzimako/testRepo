package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.dao.ReportDao;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.impl.ReportImpl;
import com.digdes.pktb.persistence.model.impl.UserImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 14:31
 */
public class ReportDaoImpl extends HibernateDaoSupport implements ReportDao {

    public Report getById(Long id) {
        return (Report) getSession().get(ReportImpl.class, id);
    }

    public Report load(Long id) {
        return (Report) getHibernateTemplate().load(ReportImpl.class, id);
    }

    public List<Report> getByParentReportId(Long id) {
        if (id == null)
            return getSession().createQuery("SELECT r FROM ReportImpl r WHERE r.parentReport IS NULL").list();

        Object parent = getSession().get(ReportImpl.class, id);
        return getSession().createQuery("SELECT r FROM ReportImpl r WHERE r.parentReport = :parent").setParameter("parent", parent).list();
    }

    public Long saveOrUpdate(Report report) {
        getSession().saveOrUpdate(report);
        return report.getId();
    }

    public void deleteById(Long id) {
        getSession().delete(getById(id));
    }

    public List<Object> debug(String query) {
        return this.getHibernateTemplate().find(query);
    }

    public List<Report> getReportByName(String name){
        DetachedCriteria criteria = DetachedCriteria.forClass(ReportImpl.class);
        criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public Report getReportByUID(String uid){
            DetachedCriteria criteria = DetachedCriteria.forClass(ReportImpl.class);
            criteria.add(Restrictions.ilike("uid", uid, MatchMode.ANYWHERE));
            List<Report> reports = getHibernateTemplate().findByCriteria(criteria);
        if(reports != null && reports.size() > 0){
            return reports.get(0);
        }
        return null;
        }
}
