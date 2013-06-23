<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="com.ibm.portal.app.component.Membership" %>
<%@ page import="javax.naming.CompositeName" %>
<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>
<%@include file="include.jsp"%>
<div id="${ns}pktb" class="pktb">
<%--  --%>

<%@include file="dialogs/adminConsole.jsp" %>

    <button onclick="pktb_project_callStatement();" style="display: none;">Конпка</button>
    <c:url value="/" var="api_url"/>
<table style="width:100%">
        <tbody>
        <tr>
            <td class="east" width="400px">
                <div class="user ui-widget-content ui-corner-all">
                    <c:if test="${isAdmin}">
                        <div class="actions">
                            <a href="#" class="overall-statistics globalStatistics" title="Общая статистика"><span class="ui-icon ui-icon-calculator"></span></a>
                            <a href="#" class="showAdminConsole" title="Консоль администратора"><span class="ui-icon ui-icon-wrench"></span></a>
                        </div>
                    </c:if>
                    <span class="username">...</span>
                </div>
                <div class="configButtons ui-widget-content ui-corner-all" style="display: none;">
                    <button class="globalStatistics">Общая статистика</button>
                </div>

                <div id="allOrFavourites" class="tree-container ">
                    <ul>
                        <li><a href="#tree">Все</a></li>
                        <li><a href="#favourites">Избранное</a></li>
                    </ul>
                    <div id="tree" class="tree"></div>
                    <div id="favourites" class="favourites"></div>
                </div>

                <div id="create-report-folder" style="display: none">
                    <form id="create-report-folder-form" class="create-report-folder" action="${api_url}/reports/createFolder.action" >
                        <input type="hidden" name="parentReportId" value="0">

                        <legend>Добавление узла</legend>
                        <table>
                            <tbody>
                            <tr>
                                <td><input id="parentId" name="parentId" type="hidden" ></td>
                            </tr>
                            <tr>
                                <td><label>Название</label></td>
                                <td><input name="name" id="name"></td>
                            </tr>
                            <tr id="uid">
                                <td><label>UID</label></td>
                                <td><input name="uid"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>

                    </form>
                </div>
            </td>
            <td class="resizer1"></td>
            <td>
                <table style="width: 100%" class="report">
                    <tbody>
                        <tr>
                            <td>

                                <div class="ui-widget-content ui-corner-all">
                                    <div class="tabs">
                                        <div class="reportFilter selected">Отчет</div>
                                        <div class="edit" style="display: none">Редактировать</div>
                                        <div class="statistics">Статистика</div>
                                        <div class="rights">Права</div>
                                        <div class="clear-fix"></div>
                                    </div>
                                    <div class="separator"></div>
                                    <div class="name">Отчет не выбран</div>
                                    <div class="filter"></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="resizer2"></td>
                        </tr>
                        <tr>
                            <td>
                                <div class="responseReportName"></div>
                                <div class="body ui-widget-content ui-corner-all"></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>

<%--div class="logs" style="display: none;"></div--%>
<%--  --%>    
</div>
