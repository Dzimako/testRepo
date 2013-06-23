package com.digdes.pktb.rolemodel.bc.impl;

import com.digdes.pktb.persistence.SpringContext;
import com.digdes.pktb.rolemodel.bc.PKTBProjectBC;
import com.digdes.pktb.rolemodel.bc.util.ComponentModelHelper;
import com.digdes.pktb.rolemodel.bc.util.ListModelHelper;
import com.digdes.pktb.rolemodel.services.RolesProvider;
import com.ibm.portal.ListModel;
import com.ibm.portal.Localized;
import com.ibm.portal.ModelException;
import com.ibm.portal.ObjectID;
import com.ibm.portal.app.component.DisplayInfo;
import com.ibm.portal.app.component.Lifecycle;
import com.ibm.portal.app.component.Membership;
import com.ibm.portal.app.exceptions.ComponentException;
import com.ibm.portal.app.model.ComponentRole;
import com.ibm.portal.app.model.Variable;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.naming.CompositeName;
import javax.naming.Context;

import javax.naming.InitialContext;
import java.io.InputStream;
import java.util.*;

public class PKTBProjectBCImpl implements Lifecycle, Membership, DisplayInfo, PKTBProjectBC {

    private static String BC_VERSION = "NEW";
    private String bc_id;//Id bc
    private String application_id; //id instance
    private RolesProvider rolesProvider;
    private static Logger logger = Logger.getLogger("bc-log-file");
    public static ClassLoader bcClassLoader;


    public PKTBProjectBCImpl() {
        logger.debug("Create BC PKTB (version default: " + BC_VERSION + ")");
        bcClassLoader = Thread.currentThread().getContextClassLoader();
        BC_VERSION = getSettingsValue("bc.version");
        logger.debug("Create new Bc version +"+BC_VERSION);
    }

    /*----------------------------------------------------------------------------------------------
                                       Lifecycle
    -----------------------------------------------------------------------------------------------*/


    //Создание экземпляра BC (событие)
    public ListModel createInstance(ListModel parameters) throws ComponentException {
        logger.debug("===============================Create Instance of BC PKTB " + BC_VERSION + " =================================");
        try {
            ArrayList<Variable> list = new ArrayList<Variable>();
            application_id = getParameter(parameters, Variable.APPLICATION_ID);
            String title = getParameter(parameters, Variable.TEXT);
            String bcID = getParameter(parameters, Variable.ID);
            logger.debug("Application id: " + application_id);
            if (null == title || title.length() == 0) {
                logger.debug("Title not activate");
                title = getSettingsValue("rolemodel.bc.title");
                logger.debug("Set title to " + title);
            }
            //Если ID для BC существет то отправляем существующий id
            if (bcID != null && bcID.length() != 0) {
                logger.debug("BC already exist with id =" + bcID);
                list.add(ComponentModelHelper.getVariable(Variable.ID, "id", "the id", bcID));
                return ListModelHelper.from(list);
            }
            //Генерируем новый ID для BC
            logger.debug("Generate new BC id: ");
            bcID = Long.toHexString(System.nanoTime());
            logger.debug("new bc_id = " + bcID);
            bc_id = bcID;
            list.add(ComponentModelHelper.getVariable(Variable.ID, "id", "the id", bcID));
            return ListModelHelper.from(list);
        } catch (Throwable t) {
            logger.debug("Exception of create BC PKTB " + t.getMessage());
            logger.debug("Bc FAILED CREATE!!!");
            throw new ComponentException(t);
        } finally {
            logger.debug("===========================Finish create instance of BC PKTB ====================================");
        }
    }

    //Получить список созданных параметров (событие)
    public ListModel getCreateParameters() {
        ArrayList<Variable> list = new ArrayList<Variable>();
        return ListModelHelper.from(list);
    }

    //Удаление экземпляра BC (событие)
    public void removeInstance(String s) throws ComponentException {
        logger.debug("==============================Remove BC instance of PKTB " + BC_VERSION + " ============================");

    }


    /* ----------------------------------------------------------------------------------------------
                                    Membership
    *----------------------------------------------------------------------------------------------*/
    //Событие добавление Principal в роль
    public void memberAdded(String component, ObjectID objectID, String role) throws ComponentException {
        try {
            logger.debug("===============================ADD MEMBER of BC PKTB " + BC_VERSION + " =================================");
            String bcVersion = getSettingsValue("bc.version");
            //Если старая версия бизнес компоненты
            if (!bcVersion.equals(BC_VERSION)) {
                logger.debug("****It's old BC****");
                String jndiName = getSettingsValue("bc.jndi");
                Context initialContext = new InitialContext();
                Membership bcComponent = (Membership) initialContext.lookup(new CompositeName(jndiName));
                bcComponent.memberAdded(component, objectID, role);
            } else { //Если новая версия бизнес компоненты
                RolesProvider rolesProviderImpl = getRolesProvider();
                boolean result = false;
                if (rolesProviderImpl != null) {
                    result = rolesProviderImpl.addPrincipal(component, objectID, role);
                }
                logger.debug("=========================== Finish Add Principal success:" + result + " ===================================");
            }
        } catch (Exception ex) {
            logger.debug("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            logger.debug("Finally and return original classLoader");
            Thread.currentThread().setContextClassLoader(bcClassLoader);
        }
    }


    //Событие получить список ролей
    public ListModel getComponentRoles(String appId) {
        try {
            logger.debug("================================Get Roles " + BC_VERSION + " ========================================");
            RolesProvider rolesProviderImpl = getRolesProvider();
            List<ComponentRole> roles = rolesProviderImpl.getRoles(appId);
            if (roles != null) {
                logger.debug("==============================Finish Return roles size:" + roles.size() + "===========================");
                return ListModelHelper.from(roles);
            } else {
                logger.debug("------------------------Finish Return roles FALSE----------------------------");
            }
        } catch (Exception ex) {
            logger.debug("Error:" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            logger.debug("Finally and return original classLoader");
            Thread.currentThread().setContextClassLoader(bcClassLoader);
        }
        return ListModelHelper.from(new ArrayList());
    }

    //Событие удаления Principal из роли

    public void memberRemoved(String component, ObjectID objectID, String role) throws ComponentException {
        try {
            logger.debug("===============================Remove Principal " + BC_VERSION + " ====================================");
            String bcVersion = getSettingsValue("bc.version");
            //Если старая версия бизнес компоненты
            if (!bcVersion.equals(BC_VERSION)) {
                 logger.debug("****It's old BC****");
                String jndiName = getSettingsValue("bc.jndi");
                Context initialContext = new InitialContext();
                Membership bcComponent = (Membership) initialContext.lookup(new CompositeName(jndiName));
                bcComponent.memberRemoved(component, objectID, role);
            } else { //Если новая версия бизнес компоненты
                RolesProvider rolesProviderImpl = getRolesProvider();
                boolean result = false;
                if (rolesProvider != null) {
                    result = rolesProviderImpl.removePrincipal(component, objectID, role);
                }
                logger.debug("=======================Finish remove principal success: " + result + " ==================================");
            }

        } catch (Exception ex) {
            logger.debug("Error:" + ex.getMessage());
        } finally {
            logger.debug("Finally and return original classLoader");
            Thread.currentThread().setContextClassLoader(bcClassLoader);
        }
    }


    /*----------------------------------------------------------------------------------------------
                                      Persistance
    ----------------------------------------------------------------------------------------------*/
    private String getParameter(ListModel parameters, String name) throws ModelException {
        Iterator params = parameters.iterator();
        String retVal = null;
        while (params.hasNext() && retVal == null) {
            Variable param = (Variable) params.next();
            if (name.equals(param.getVariableName())) {
                retVal = param.getValue().toString();
            }
        }
        return retVal;
    }

    //Получить значение настроек
    public static String getSettingsValue(String key) {
        InputStream is = null;
        try {
            is = PKTBProjectBCImpl.class.getClassLoader().getResourceAsStream("settings.properties");
            Properties properties = new Properties();
            properties.load(is);
            return new String(properties.get(key).toString().getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    //Получить сервис для работы с пользователями
    public RolesProvider getRolesProvider() {
        logger.debug("=========================Get Roles Provider " + BC_VERSION + " ===========================");
        try {
            ApplicationContext appContext = SpringContext.getApplicationContext();
            if (appContext != null) {
                Thread.currentThread().setContextClassLoader(SpringContext.getOriginalClassLoader());
                return rolesProvider = (RolesProvider) appContext.getBean("rolesProviderService");
            } else {
                logger.debug("Error: Application Context not initialize");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("========================================================================================");
        return null;
    }

    /* ---------------------------------------------------------------------------------------------
                                                DISPLAY INFO
    -----------------------------------------------------------------------------------------------*/
    public Localized getDisplayInfo(String s) {
        logger.debug("-----------------------------Get Display Info " + BC_VERSION + "  ---------------------------");
        return ComponentModelHelper.getLocalized(getSettingsValue("rolemodel.bc.title"), getSettingsValue("rolemodel.bc.displayInfo"));
    }
}
