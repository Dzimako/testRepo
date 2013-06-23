package com.digdes.pktb.rolemodel.services.impl;

import com.digdes.pktb.persistence.dao.*;
import com.digdes.pktb.persistence.helpers.PKTBHelper;
import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.ReportRights;
import com.digdes.pktb.persistence.model.User;
import com.digdes.pktb.persistence.model.UserRole;
import com.digdes.pktb.persistence.model.impl.ReportRightsImpl;
import com.digdes.pktb.persistence.model.impl.UserImpl;
import com.digdes.pktb.persistence.util.UserAttributesPuma;
import com.digdes.pktb.rolemodel.bc.util.ComponentModelHelper;
import com.digdes.pktb.rolemodel.bc.util.PumaObjectTypes;
import com.digdes.pktb.rolemodel.services.RolesProvider;
import com.ibm.portal.ObjectID;
import com.ibm.portal.ObjectType;
import com.ibm.portal.app.model.ComponentRole;
import com.ibm.portal.um.Group;
import com.ibm.portal.um.PumaHome;
import org.apache.log4j.Logger;

import java.util.*;

public class RolesProviderImpl implements RolesProvider {

    private PumaHome pumaHome;
    private RailwayDao railwayDao;
    private ReportDao reportDao;
    private ReportRightsDao reportRightsDao;
    private UserDao userDao;
    private UserRoleDao userRoleDao;
    private Logger logger = Logger.getLogger("bc-log-file");

    public RolesProviderImpl() {
    }

    public void setPumaHome(PumaHome pumaHome) {
        this.pumaHome = pumaHome;
    }

    public void setRailwayDao(RailwayDao railwayDao) {
        this.railwayDao = railwayDao;
    }

    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public void setReportRightsDao(ReportRightsDao reportRightsDao) {
        this.reportRightsDao = reportRightsDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    //Добавление пользователя или группы пользователей в роль
    public boolean addPrincipal(String component, ObjectID objectId, String role) {
        logger.debug("---------Add Principal----------");
        try {
            if (objectId != null) {
                logger.debug("Input values:");
                logger.debug("\t component: " + component);
                logger.debug("\t objectID: " + objectId);
                logger.debug("\t role: " + role);

                ObjectType objectType = objectId.getObjectType();
                if (objectType.toString().equals(PumaObjectTypes.USER.name())) {
                    com.ibm.portal.um.User user = pumaHome.getLocator().findUserByObjectID(objectId);
                    return addUser(user, role, component);
                } else if (objectType.toString().equals(PumaObjectTypes.USER_GROUP.name())) {
//                    Group group = pumaHome.getLocator().findGroupByObjectID(objectId);
                    return false;//addGroupOfUser(group, role, component);
                }
            } else {
                logger.debug("objectId is null");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //Получить список ролей
    public List<ComponentRole> getRoles(String component) {
        logger.debug("---------Search roles----------");
        List<ComponentRole> rolesBean = new ArrayList<ComponentRole>();
        try {
            List<UserRole> roles = userRoleDao.listAllRoles();
            logger.debug("find " + roles.size() + " roles.");
            for (UserRole role : roles) {
                if (role.getRoleKey().equals(PKTBHelper.getSettingsValue("user.role.key.admin"))){
                    rolesBean.add(ComponentModelHelper.getComponentRole(role.getRoleKey(), role.getRoleName(), role.getDescription() != null ? role.getDescription() : ""));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rolesBean;
    }

    //Удалить пользователя или группу пользователей из роли
    public boolean removePrincipal(String component, ObjectID objectId, String role) {
        logger.debug("---------Remove Principal----------");
        try {
            if (objectId != null) {
                logger.debug("Input values:");
                logger.debug("\t component: " + component);
                logger.debug("\t objectID: " + objectId);
                logger.debug("\t role: " + role);
                ObjectType objectType = objectId.getObjectType();
                if (objectType.toString().equals(PumaObjectTypes.USER.name())) {
                    com.ibm.portal.um.User user = pumaHome.getLocator().findUserByObjectID(objectId);
                    return removeUser(user, role, component);
                } else if (objectType.toString().equals(PumaObjectTypes.USER_GROUP.name())) {
//                    Group group = pumaHome.getLocator().findGroupByObjectID(objectId);
                    return false;//removeGroupOfUser(group, role, component);
                }
            } else {
                logger.debug("objectId is null");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //Добавление пользователя
    private boolean addUser(com.ibm.portal.um.User user, String roleName, String appId) {
        logger.debug("Add  role for user");
//        try {
//            applicationService.initApplication(appId);
//        } catch (Exception e) {
//            logger.debug("application not created");
//        }
//        if (!applicationDAO.isApplication(appId)){
//            logger.debug("applicationDAO.isApplication(appId) == false");
//            return false;
//        }
        try {
            if (userRoleDao.listUserRolesByKey(roleName).size()>0) {
                List<String> searchAttributes = Arrays.asList(UserAttributesPuma.CN.getValue(),
                        UserAttributesPuma.GIVEN_NAME.getValue(),
                        UserAttributesPuma.SN.getValue(),
                        UserAttributesPuma.EMAIL.getValue());
                logger.debug("Search attributes: " + searchAttributes);
                Map userAttributeMap = pumaHome.getProfile().getAttributes(user, searchAttributes);
                String cnUser = userAttributeMap.get(UserAttributesPuma.CN.getValue()).toString();
                String email = userAttributeMap.get(UserAttributesPuma.EMAIL.getValue()).toString();
                String givenName = userAttributeMap.get(UserAttributesPuma.GIVEN_NAME.getValue()).toString();
                logger.debug("cnUser: " + cnUser);
                if (cnUser != null) {
                    User userLoaded = userDao.getUserByCN(cnUser);
                    if (userLoaded == null) {
                        logger.debug("User with cn = " + cnUser + " not found in database");
                        userLoaded = new UserImpl();
                        userLoaded.setCn(cnUser);
                        userLoaded.setEmail(email);
                        userLoaded.setUserRole(userRoleDao.loadUserRoleByKey(roleName));
                        userLoaded.setDisplayName(givenName);
                        userLoaded.setRailway(railwayDao.getByCode(PKTBHelper.getSettingsValue("rolemodel.railway.code.default")));
                        userDao.saveOrUpdate(userLoaded);
                        logger.debug("User " + cnUser + " created and set role " + roleName);
                        return true;
                    } else {
                        userLoaded.setUserRole(userRoleDao.loadUserRoleByKey(roleName));
                        userDao.saveOrUpdate(userLoaded);
                        List<Report> reports = reportDao.getByParentReportId(null);
                        for (Report report: reports){
                            reportRightsDao.saveOrUpdate(new ReportRightsImpl(userLoaded, report));
                        }
                    }
                } else {
                    logger.debug("Parametr cn of user not found");
                    return false;
                }
            } else {
                logger.debug("Role with name " + roleName + " not found");
                return false;
            }
        } catch (Throwable e) {
            logger.debug("Error addUser: "+ e.getMessage());
        }
        return false;
    }

    //Удаление пользователя из роли
    private boolean removeUser(com.ibm.portal.um.User user, String roleName, String appId) {
        logger.debug("Remove user from role");
        try {
            if (userRoleDao.listUserRolesByKey(roleName).size()>0) {
                Map userAttributeMap = pumaHome.getProfile().getAttributes(user, Arrays.asList(UserAttributesPuma.CN.getValue()));
                String cnUser = userAttributeMap.get(UserAttributesPuma.CN.getValue()).toString();
                if (cnUser != null) {
                    User userLoaded = userDao.getUserByCN(cnUser);
                    if (userLoaded != null) {
                        userLoaded.setUserRole(null);
                        userDao.saveOrUpdate(userLoaded);
                        logger.debug("Role " + roleName + " delete from user " + cnUser);
                    } else {
                        logger.debug("User " + cnUser + " not found");
                        return false;
                    }
                } else {
                    logger.debug("Parametr cn of user not found so as constant");
                    return false;
                }
            } else {
                logger.debug("Role with name " + roleName + " not found");
                return false;
            }
            logger.debug("Compleate");
        } catch (Throwable e) {
            logger.debug("Error removeUser:"+ e.getMessage());
        }
        return false;
    }

}
