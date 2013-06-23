package com.digdes.pktb.persistence.services;

import com.digdes.pktb.persistence.beans.ajaxbeans.model.UserBean;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.ExistsUserDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UserDTO;
import com.digdes.pktb.persistence.beans.ajaxbeans.responses.UsersAndGroupsResponse;
import com.digdes.pktb.persistence.model.User;
import org.springframework.security.AccessDeniedException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 14:49
 */
public interface UserService {

    public User getCurrentUser();

    public UserBean getCurrentUser(RenderRequest request, RenderResponse response) throws AccessDeniedException;

    public UserBean getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException;

    public List<String> autocompleteUsers(String userName);

    public List<UserBean> autocompleteUserBeans(String userName);

    public UsersAndGroupsResponse autocompleteUserAndGroups(String userOrGroupName);

    public List<UserDTO> getByRailwayId(Long id);

    public List<UserDTO> getByGroupId(Long id);

    public ExistsUserDTO attachUserByCnToRailwayById(String cn, Long id, Boolean overwrite, String displayName);

    public void deleteUserByCn(String cn);

    public void attachUserByCnToGroupById(String cn, Long id, String displayName);

    public void detachUserByCnFromGroupById(String cn, Long id);

    public List<UserDTO> usersAutocomplete(String search);

    void moveUserByCnFromGroup1ByIdToGroup2ById(String cn, Long id1, Long id2);

}
