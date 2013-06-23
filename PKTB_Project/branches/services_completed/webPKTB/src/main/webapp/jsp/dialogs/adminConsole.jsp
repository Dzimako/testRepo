<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="userRightsDialog" class="userRightsDialog" style="display: none">
    <table class="wide">
        <tbody>
        <tr>
            <td>
                <div id="rightsReportTreeContainer" class="rightsReportTreeContainer ui-widget-content ui-corner-all">
                    <div id="userRightsTree" class="userRightsTree"></div>
                </div>
            </td>
            <td style="width: 3em; vertical-align: middle; text-align: center;">
                <button id="giveRights" disabled="disabled" data-jquery-ui="button: {text: false, icons: {primary: 'ui-icon-arrowthick-1-e'}}">Добавить права</button>
            </td>
            <td>
                <div class="listContainer ui-widget-content ui-corner-all">
                    <ul id="availibleList" class="availibleList"></ul>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>













<div id="adminConsole" class="adminConsole" style="display: none" title="Консоль администратора">
    <table class="wide">
        <tbody>
        <tr>
            <td class="half">
                <div id="railwayListGroupListRadioContainer"
                     class="railwayListGroupListRadioContainer"
                     data-jquery-ui="buttonset: {}">
                    <input type="radio"
                           id="railwayListRadio"
                           name="railwayListGroupListRadio"
                           checked="checked"/><label for="railwayListRadio">Список дорог</label>
                    <input type="radio"
                           id="groupListRadio"
                           name="railwayListGroupListRadio"/><label for="groupListRadio">Список групп</label>
                </div>
            </td>
            <td class="half">

            </td>
        </tr>
        <tr>
            <td>
                <div id="railwayListGroupListContainer" class="listContainer railwayListGroupListContainer ui-widget-content ui-corner-all">
                    <ul id="railwayList" class="railwayList"></ul>
                    <ul id="groupList" class="groupList" style="display: none;"></ul>
                </div>
            </td>
            <td>
                <div id="userListContainer" class="listContainer userListContainer ui-widget-content ui-corner-all">
                    <ul id="userList" class="userList"></ul>
                </div>
                <form id="editRailwayForm"
                      class="editRailwayForm ui-widget-content ui-corner-all">
                    <label>Название</label>
                    <input name="name" class="input" >
                    <label>Код</label>
                    <input name="code" size="3" class="input" >
                </form>
                <form id="editGroupForm"
                      class="editGroupForm ui-widget-content ui-corner-all">
                    <label>Название</label>
                    <input name="name" class="input" >
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <button id="addRailwayButton" class="addRailwayButton"  data-jquery-ui="button: {icons: {primary: 'ui-icon-plusthick'}}">Добавить</button>
                <button id="addGroupButton" class="addGroupButton" style="display: none" data-jquery-ui="button: {icons: {primary: 'ui-icon-plusthick'}}">Добавить</button>
            </td>
            <td>
                <form id="addUserForm" action="#" class="addUserForm">
                    <input id="addUserDisplayNameInput"
                           name="displayName"
                           placeholder="Имя пользователя"
                           class="addUserDisplayNameInput ui-widget-content ui-corner-all"
                           disabled="disabled">

                </form>
            </td>
        </tr>
        </tbody>
    </table>
</div>