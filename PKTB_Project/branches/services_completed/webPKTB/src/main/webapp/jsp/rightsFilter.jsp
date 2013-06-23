<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<script type="text/javascript">
    var reportRightsBeans = jQuery.parseJSON('${reportRightsBeans}');
</script>
<div class="separator"></div>
<div class="rightsFilterPlaceholder">
    <div class="radioRights">
        <input type="radio" id="radio1" name="radio"/><label for="radio1">Все</label>
        <input type="radio" id="radio2" name="radio"/><label for="radio2">Выборочно</label>
    </div>
    <div class="filterItem">
        <label>Имя пользователя или группа</label>
        <input class="input autocompleteUsers" type="text" value=""/>
    </div>
    <table class="usersAndGroup">
        <tbody>
        <tr>
            <td>
                <div class="usersWrapper">
                    <table class="users">
                        <thead>
                            <tr>
                                <th colspan="2">
                                    Пользователи
                                </th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="groupsWrapper">
                    <table class="groups">
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <button class="refresh">Обновить</button>
            </td>
        </tr>
        </tbody>
    </table>

</div>