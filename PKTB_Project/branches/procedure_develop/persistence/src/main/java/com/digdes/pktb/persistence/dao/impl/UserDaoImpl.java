package com.digdes.pktb.persistence.dao.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserDTO;
import com.digdes.pktb.persistence.dao.UserDao;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.impl.UserImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Serdyuk.A
 * Date: 25.09.12
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    public User load(Long userId) {
        return (User) getHibernateTemplate().load(UserImpl.class, userId);
    }

    public User getUserByCN(String userCN){
        DetachedCriteria criteria = DetachedCriteria.forClass(UserImpl.class);
        criteria.add(Expression.like("cn", userCN));
        List<User> users = getHibernateTemplate().findByCriteria(criteria);
        if(users != null && users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    public List<User> getUserByDisplayName(String displayName){
        DetachedCriteria criteria = DetachedCriteria.forClass(UserImpl.class);
        criteria.add(Restrictions.ilike("displayName", displayName, MatchMode.ANYWHERE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public List<User> getByRailwayId(Long id) {
        if (id == null)
            return getSession().createQuery("SELECT u FROM UserImpl u WHERE u.railway IS NULL").list();
        return getSession().createQuery("SELECT u FROM UserImpl u WHERE u.railway.id = :id").setLong("id", id).list();
    }

    public void saveOrUpdate(User user) {
        getHibernateTemplate().saveOrUpdate(user);
    }

    public void delete(User user) {
        getHibernateTemplate().delete(user);
    }

    public List<User> search(String search) {
        String like = "%" + search.replace("  ", " ").replace(" ", "%") + "%";
        System.out.println(like);
        return getSession().createQuery("SELECT u FROM UserImpl u WHERE u.cn LIKE :search").setString("search", like).list();
        //return new ArrayList<UserDTO>();

    }
}
