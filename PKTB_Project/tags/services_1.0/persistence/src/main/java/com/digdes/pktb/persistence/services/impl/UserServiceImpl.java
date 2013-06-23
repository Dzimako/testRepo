package com.digdes.pktb.persistence.services.impl;

import com.digdes.pktb.persistence.beans.ajaxbeans.ExistsUserDTOMapper;
import com.digdes.pktb.persistence.beans.ajaxbeans.UserDTOMapper;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.RailwayBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserGroupBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserRoleBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ExistsUserDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UsersAndGroupsResponse;
import com.digdes.pktb.persistence.dao.RailwayDao;
import com.digdes.pktb.persistence.dao.UserDao;
import com.digdes.pktb.persistence.dao.UserGroupDao;
import com.digdes.pktb.persistence.dao.UserRoleDao;
import com.digdes.pktb.persistence.helpers.PKTBHelper;
import com.digdes.pktb.persistence.model.Railway;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.UserGroup;
import com.digdes.pktb.persistence.model.UserRole;
import com.digdes.pktb.persistence.model.impl.UserImpl;
import com.digdes.pktb.persistence.services.UserService;
import com.ibm.portal.um.PumaHome;
import com.ibm.wps.pe.pc.std.core.PortletUtils;
import org.springframework.security.AccessDeniedException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 14:50
 */
public class UserServiceImpl implements UserService{

    private PumaHome pumaHome;
    private UserDao userDao;
    private UserGroupDao userGroupDao;
    private UserRoleDao userRoleDao;
    private RailwayDao railwayDao;

    public void setPumaHome(PumaHome pumaHome) {
        this.pumaHome = pumaHome;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserGroupDao(UserGroupDao userGroupDao) {
        this.userGroupDao = userGroupDao;
    }

    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    // Метод создает или возвращает текущего пользователя.

    public UserBean getCurrentUser(RenderRequest request, RenderResponse response) throws AccessDeniedException {
        HttpServletRequest servletRequest = PortletUtils.getIncludeServletRequest(request);
        HttpServletResponse servletResponse = PortletUtils.getIncludeServletResponse(response);
        return getCurrentUser(servletRequest, servletResponse);
    }

    // Получить именно текущего пользователя, а не dto  НЕ РАБОТАЕТ
    public User getCurrentUser() {
        try {
            com.ibm.portal.um.User pumaUser = pumaHome.getProfile().getCurrentUser();
            List<String> searchAttributes = Arrays.asList("cn", "uid");

            Map userAttributeMap = pumaHome.getProfile().getAttributes(pumaUser, searchAttributes);
            String gotCn = (String) userAttributeMap.get("cn");
            String gotUID = (String) userAttributeMap.get("uid");
            User user = userDao.getUserByCN(gotUID);

            System.out.print(">>>>>>>>>>>>" + user.getCn() + ">>>" + gotUID  + "> "+ gotCn + ">>" + user.getDisplayName());
            return user;
        } catch (Throwable e) {
            System.out.println(">>>>>>>>>>Error while getting current user: "+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    // Метод создает или возвращает текущего пользователя.

    public UserBean getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException {

            try {

                com.ibm.portal.um.User pumaUser = pumaHome.getProfile().getCurrentUser();
//                logger.debug("Current user: " + pumaUser);
                UserBean userBean = new UserBean();
                List<String> searchAttributes = Arrays.asList("cn",
                        "displayName",
                        "uid",
                        "ibm-primaryEmail");

                Map userAttributeMap = pumaHome.getProfile().getAttributes(pumaUser, searchAttributes);
                String gotCn = (String) userAttributeMap.get("cn");
                String gotUID = (String) userAttributeMap.get("uid");
//                logger.debug("CN of entered user: " + gotCn);
//                logger.debug("UID of entered user: " + gotUID);
                User user = userDao.getUserByCN(gotCn);

                UserRole anonymousRole = userRoleDao.loadUserRoleByKey(PKTBHelper.getSettingsValue("user.role.key.anonymous"));
                UserRoleBean anonymousRoleBean = new UserRoleBean(anonymousRole.getRoleKey(), anonymousRole.getRoleName());
                if (user != null) {                        //Если пользователь занесен в базу
                    userBean.setUserId(user.getId());
                    userBean.setCn(user.getCn());
                    userBean.setDisplayName(user.getDisplayName());
                    userBean.setRailway(new RailwayBean(user.getRailway().getName(),user.getRailway().getCode()));
                    if (user.getUserRole() != null)
                        userBean.setUserRoleBean(new UserRoleBean(user.getUserRole().getRoleKey(), user.getUserRole().getRoleName()));
                    else
                        userBean.setUserRoleBean(anonymousRoleBean);
                } else {                                //Если пользователь не занесен в базу
                    userBean.setUserRoleBean(anonymousRoleBean);
                }
                return userBean;
            } catch (Throwable e) {
//                logger.error("Error while getting current user: ", e);
                System.out.println("Error while getting current user: "+ e.getMessage());
                e.printStackTrace();
            }
            return null;
    }

    public List<String> autocompleteUsers(String userDisplayName){
        List<String> userDisplayNames = new ArrayList<String>();
        List<User> users = userDao.getUserByDisplayName(userDisplayName);
        for (User user : users){
            userDisplayNames.add(user.getDisplayName());
        }
        return userDisplayNames;
    }

    public List<UserBean> autocompleteUserBeans(String userDisplayName){
        List<UserBean> userBeans = new ArrayList<UserBean>();
        List<User> users = userDao.getUserByDisplayName(userDisplayName);
        for (User user : users){
            UserBean userBean = new UserBean(user.getId(), user.getDisplayName());
            userBeans.add(userBean);
        }
        return userBeans;
    }

    public UsersAndGroupsResponse autocompleteUserAndGroups(String userOrGroupName){
        List<UserBean> userBeans = new ArrayList<UserBean>();
        List<UserGroupBean> userGroupBeans = new ArrayList<UserGroupBean>();
        List<User> users = userDao.getUserByDisplayName(userOrGroupName);
        List<UserGroup> userGroups = userGroupDao.getUserGroupByName(userOrGroupName);
        for (User user : users){
            UserBean userBean = new UserBean(user.getId(), user.getDisplayName());
            userBeans.add(userBean);
        }
        for (UserGroup userGroup : userGroups){
            UserGroupBean userGroupBean = new UserGroupBean(userGroup.getId(), userGroup.getName());
            userGroupBeans.add(userGroupBean);
        }
        return new UsersAndGroupsResponse(userBeans, userGroupBeans);
    }

    public List<UserDTO> getByRailwayId(Long id) {
        if (id == null)
            return new ArrayList<UserDTO>(0);

        return UserDTOMapper.map(userDao.getByRailwayId(id));
    }

    public List<UserDTO> getByGroupId(Long id) {
        if (id == null)
            return new ArrayList<UserDTO>(0);

        UserGroup userGroup = userGroupDao.load(id);
        if (userGroup == null)
            return new ArrayList<UserDTO>(0);
        return UserDTOMapper.map(userGroup.getUsers());
    }

    public ExistsUserDTO attachUserByCnToRailwayById(String cn, Long id, Boolean overwrite, String displayName) {

        if (cn == null || id == null)
            return null;

        Railway railway = railwayDao.getById(id);
        User user = userDao.getUserByCN(cn);

        if (user == null) {
            user = new UserImpl();
            user.setCn(cn);
            user.setRailway(railway);
            // TODO
            user.setDisplayName(displayName);
            userDao.saveOrUpdate(user);
            return ExistsUserDTOMapper.map(false, user);
        } else {
            if (user.getRailway() == null) {
                user.setRailway(railway);
                // TODO
                user.setDisplayName(displayName);
                userDao.saveOrUpdate(user);
                return ExistsUserDTOMapper.map(false, user);
            } else if (!overwrite)
                return ExistsUserDTOMapper.map(true, user);
            else {
                user.setRailway(railway);
                // TODO
                user.setDisplayName(displayName);
                userDao.saveOrUpdate(user);
                return ExistsUserDTOMapper.map(true, user);
            }
        }
    }

    public void deleteUserByCn(String cn) {
        if (cn != null) {
            User user = userDao.getUserByCN(cn);
            if (user != null)
                userDao.delete(user);
        }
    }

    public void attachUserByCnToGroupById(String cn, Long id, String displayName) {
        if (cn != null && id != null) {
            User user = userDao.getUserByCN(cn);
            if (user == null) {
                user = new UserImpl();
                user.setCn(cn);
                user.setDisplayName(displayName);
                userDao.saveOrUpdate(user);
            }
            UserGroup userGroup = userGroupDao.load(id);
            userGroup.getUsers().add(user);
        }
    }

    public void detachUserByCnFromGroupById(String cn, Long id) {
        if (cn != null && id != null) {
            User user = userDao.getUserByCN(cn);
            if (user != null) {
                UserGroup userGroup = userGroupDao.load(id);
                userGroup.getUsers().remove(user);
            }
        }
    }

    public List<UserDTO> usersAutocomplete(String search) {
        if (search != null && search.length() > 0)
            return UserDTOMapper.map(userDao.search(search));
        return new ArrayList<UserDTO>();
    }

    public void moveUserByCnFromGroup1ByIdToGroup2ById(String cn, Long id1, Long id2) {
        if (cn == null || id1 == null || id2 == null)
            return;

        User user = userDao.getUserByCN(cn);
        UserGroup group1 = userGroupDao.load(id1);
        UserGroup group2 = userGroupDao.load(id2);

        group2.getUsers().add(user);
        group1.getUsers().remove(user);
    }

    public RailwayDao getRailwayDao() {
        return railwayDao;
    }

    public void setRailwayDao(RailwayDao railwayDao) {
        this.railwayDao = railwayDao;
    }
}
