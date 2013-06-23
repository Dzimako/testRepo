<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    var pktb_reportId = ${id};
    var pktb_context = '<c:url value="/"/>';
    var pktb_namespace = '';
</script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/report.css"/>"/>
<script type="text/javascript" src="<c:url value="/js/report.js"/>"></script>

<%--Report placeholder--%>
<%--#${id}--%>

<div class="pktb-reportPlaceholder">

    <input class="pktb-reportId" type='text' name='newsPerPage' value="${id}"/>
    <button class="pktb-reportPlaceholderSubmit">Запросить отчет</button>

</div>
