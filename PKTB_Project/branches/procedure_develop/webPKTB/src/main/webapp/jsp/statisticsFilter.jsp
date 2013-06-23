<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<div class="separator"></div>
<div class="statisticsFilterPlaceholder">
    <div class="filterItem" paramkey="reportName">
        <label>Наименование отчета</label>
        <input class="input autocompleteReports" type="text" value=""/>
    </div>
    <div class="filterItem" paramkey="dateOfDownloadBegin">
        <label>Начальная дата</label>
        <input class="input datePickerInput_withTime" type="text" value=""/>
    </div>
    <div class="filterItem" paramkey="dateOfDownloadEnd">
        <label>Конечная дата</label>
        <input class="input datePickerInput_withTime" type="text" value=""/>
    </div>
    <div class="filterItem" paramkey="userDisplayName">
        <label>Пользователь</label>
        <input class="input autocompleteUsers" type="text" value=""/>
    </div>
    <div class="filterItem" paramkey="success">
        <label>Результат</label>
        <select class="input">
            <option value="">Все</option>
            <option value="true">Успешно</option>
            <option value="false">Не успешно</option>
        </select>
    </div>

    <button class="statisticsFilterSubmit">Запросить статистику</button>

</div>