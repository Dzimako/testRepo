package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.dao.UserRoleDao;
import com.digdes.pktb.persistence.model.UserRole;
import com.digdes.pktb.persistence.model.impl.UserRoleImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 27.09.12
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleDaoImpl extends HibernateDaoSupport implements UserRoleDao {
    public UserRole load(Long roleId) {
        return (UserRole) getHibernateTemplate().load(UserRoleImpl.class, roleId);
    }

    public UserRole loadUserRoleByKey(String key){
        DetachedCriteria criteria = DetachedCriteria.forClass(UserRoleImpl.class);
        criteria.add(Expression.like("roleKey", key));
        List<UserRole> userRoles = getHibernateTemplate().findByCriteria(criteria);
        if(userRoles != null && userRoles.size() > 0){
            return userRoles.get(0);
        }
        return null;
    }

    public List<UserRole> listUserRolesByKey(String key){
        DetachedCriteria criteria = DetachedCriteria.forClass(UserRoleImpl.class);
        criteria.add(Expression.like("roleKey", key));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public List<UserRole> listAllRoles(){
        return getHibernateTemplate().loadAll(UserRoleImpl.class);
    }
}
