<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/report.css"/>"/>
<%--<script type="text/javascript" src="<c:url value="/js/report.js"/>"></script>--%>

${reportFilters}

<div class="separator"></div>
<div class="pktb-reportPlaceholder">

    <input class="pktb-reportId" type='text' name='newsPerPage' value="${id}"/>
    <button class="pktb-reportPlaceholderSubmit" style="color: #D40513; font-weight: bold;">Запросить отчет</button>
    <button class="getReportExcel">Получить в формате Excel</button>
    <button class="clearReportBody">Очистить</button>
    <button id="addToFavourites">Добавить в избранное</button>

</div>