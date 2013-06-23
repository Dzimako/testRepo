package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.dao.UserGroupDao;
import com.digdes.pktb.persistence.model.UserGroup;
import com.digdes.pktb.persistence.model.impl.UserGroupImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 03.10.12
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupDaoImpl extends HibernateDaoSupport implements UserGroupDao {
    public List<UserGroup> getUserGroupByName(String name){
        DetachedCriteria criteria = DetachedCriteria.forClass(UserGroupImpl.class);
        criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public UserGroup load(Long id){
        return (UserGroup) getHibernateTemplate().load(UserGroupImpl.class, id);
    }

    public List<UserGroup> getAll() {
        return getSession().createQuery("SELECT g FROM UserGroupImpl g").list();
    }

    public UserGroup getByIdWithUsers(Long id) {
        List<UserGroup> ug = getSession().createQuery("SELECT g FROM UserGroupImpl g LEFT JOIN FETCH g.users WHERE g.id = :id").setParameter("id", id).list();
        System.out.print(ug.size() + ">>><");
        if (ug.size() != 1)
            return null;
        else return ug.get(0);
    }

    public void saveOrUpdate(UserGroup userGroup) {
        getHibernateTemplate().saveOrUpdate(userGroup);
    }

    public void deleteById(Long id) {
        UserGroup userGroup = load(id);
        //getSession().delete(userGroup);
        System.out.print("deletion disabled");
    }

    public void delete(UserGroup userGroup) {
        getHibernateTemplate().delete(userGroup);
    }
}
