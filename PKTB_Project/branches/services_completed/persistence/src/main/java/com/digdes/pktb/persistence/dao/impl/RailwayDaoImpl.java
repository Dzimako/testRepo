package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.dao.RailwayDao;
import com.digdes.pktb.persistence.model.Railway;
import com.digdes.pktb.persistence.model.impl.RailwayImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 28.09.12
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class RailwayDaoImpl extends HibernateDaoSupport implements RailwayDao {

    public List<Railway> getAll() {
        return getSession().createQuery("SELECT r FROM RailwayImpl r").list();
    }

    public List<Integer> getUsersCountForAll() {
        return getSession().createQuery("SELECT r FROM RailwayImpl r").list();
    }

    public Railway getByCode(String code) {
        DetachedCriteria criteria = DetachedCriteria.forClass(RailwayImpl.class);
        criteria.add(Restrictions.ilike("code", code, MatchMode.ANYWHERE));
        List<Railway> railways = getHibernateTemplate().findByCriteria(criteria);
        if (railways.size() > 0)
            return railways.get(0);
        return null;
    }

    public Railway getById(Long id) {
        return (Railway) getHibernateTemplate().load(RailwayImpl.class, id);
    }

    public void saveOrUpdate(Railway railway) {
        getHibernateTemplate().saveOrUpdate(railway);
    }

    public void delete(Railway railway) {
        getHibernateTemplate().delete(railway);
    }
}
