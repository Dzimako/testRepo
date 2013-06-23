<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/report.css"/>"/>
<%--<script type="text/javascript" src="<c:url value="/js/report.js"/>"></script>--%>

${reportFilters}

<%--<c:if test="${id == 405}">
<form action="#" class="filters">
    <div class="filter">
        <label>Депо приписки</label>
        <select name="depoP">
            <option value="0">все</option>
            <option>dict_tn_pasp_depo</option>
        </select>
    </div>
    <div class="filter">
        <label>Отчетные сутки</label>
        <input name="dateOtch" data-widget="datepicker">
    </div>
    <div class="filter">
        <label>Вид тяги</label>
        <select name="vidT">
            <option value="*">все</option>
            <option value="1">Электровозы</option>
            <option value="2">Тепловозы</option>
        </select>
    </div>
    <div class="filter">
        <label>Серия локомотива</label>
        <select name="serLoc">
            <option value="*">все</option>
            <option>dict_tn_ser_loc_rs_kod_ser_mnem_ser</option>
        </select>
    </div>
    <div class="filter">
        <label>Род службы ТПС</label>
        <select name="rodS">
            <option value="*">все</option>
            <option value="1">грузовые</option>
            <option value="2">пассажирские</option>
            <option value="3">грузопассажирские</option>
            <option value="4">маневровые</option>            
        </select>
    </div>
    <div class="filter">
        <label>Род тока</label>
        <select name="rodT">
            <option value="*">все</option>
            <option value="1">переменного</option>
            <option value="2">постоянного</option>
            <option value="3">двойного питания</option>
        </select>
    </div>
    <div class="filter">
        <label>Ширина колеи</label>
        <select name="shirinaKolei">
            <option value="*">все</option>
            <option value="1520">1520 мм</option>
            <option value="1435">1435 мм</option>
            <option value="1067">1067 мм</option>
            <option value="750">750 мм</option>
        </select>
    </div>
    <div class="filter">
        <label>Единица выдачи</label>
        <select name="edVidachi">
            <option value="1">с уч. кострукт. ед.</option>
            <option value="2">в тяговых ед.</option>
        </select>
    </div>
</form>

</c:if>
<c:if test="${id == 409}">
    <form action="#" class="filters">
        <div class="filter">
            <label>Год</label>
            <select name="N0">
                <option selected="selected">2012</option>
                <option>dict_year</option>
            </select>
        </div>
        <div class="filter">
            <label>Месяц</label>
            <select name="N1">
                <option value="01">Янв.</option>
                <option value="02">Февр.</option>
                <option value="03">Март</option>
                <option value="04">Апр.</option>
                <option value="05">Май</option>
                <option value="06">Июнь</option>
                <option value="07">Июль</option>
                <option value="08">Авг.</option>
                <option value="09">Сент.</option>
                <option value="10">Окт.</option>
                <option value="11">Нояб.</option>
                <option value="12">Дек.</option>
            </select>
        </div>
        <div class="filter">
            <label>День</label>
            <select name="N2">
            <c:forEach var="day" begin="1" end="31">
                <option>${day}</option>
            </c:forEach>
            </select>
        </div>
        <div class="filter">
            <label>Час</label>
            <select name="N3">
            <c:forEach var="hour" begin="0" end="23">
                <option>${hour}</option>
            </c:forEach>
            </select>
        </div>
        <div class="filter">
            <label>Минута</label>
            <select name="N4">
            <c:forEach var="minute" begin="0" end="59">
                <option>${minute}</option>
            </c:forEach>
            </select>
        </div>
        <div class="filter">
            <label>Вид тяги</label>
            <select name="N10">
                <option value="">все</option>
                <option value="001">Электровозы</option>
                <option value="002">Тепловозы</option>
            </select>
        </div>
        <div class="filter">
            <label>Выдача в единицах</label>
            <select name="N11">
                <option value="1">Тяговые единицы</option>
                <option value="2">Единицы конструктивного исполнения</option>
            </select>
        </div>
        <div class="filter">
            <label>Признак разложения</label>
            <select name="N12">
                <option value="1">По депо приписки</option>
                <option value="2">По депо приписки и сериям</option>
            </select>
        </div>
    </form>
</c:if>--%>
<div class="separator"></div>
<div class="pktb-reportPlaceholder">

    <input class="pktb-reportId" type='text' name='newsPerPage' value="${id}"/>
    <button class="pktb-reportPlaceholderSubmit" style="color: #D40513; font-weight: bold;">Запросить отчет</button>
    <button class="getReportExcel">Получить в формате Excel</button>
    <button class="clearReportBody">Очистить</button>
    <button id="addToFavourites">Добавить в избранное</button>

</div>