<%--
  Created by IntelliJ IDEA.
  User: Panfilov.V
  Date: 12.09.12
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui-1.8.23.custom.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables_themeroller.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/TableTools_JUI.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui-timepicker-addon.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/pktb1.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/pktb.css"/>"/>


<script type="text/javascript" src="<c:url value="/js/lib/json2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/datatables/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/datatables/ZeroClipboard.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/datatables/TableTools.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jstree/jquery.jstree.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery-ui-timepicker-addon.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/date.format.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery.bgiframe.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/helpers.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/pktb.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/pktbAdminConsole.js"/>"></script>

<c:set var="ns"><portlet:namespace/></c:set>

<script type="text/javascript">
    var ANONYMOUS_RIGHTS_MESSAGE = '<spring:message code="spring.anonymousRightsMessage"/>';
    var REPORT_NOT_SELECTED = '<spring:message code="spring.reportNotSelected"/>';
    var DISPLAY_NAME_UNKNOWN = '<spring:message code="user.displayName.unknown"/>';
    var FILTERS_UNAVAILABLE_MESSAGE = '<spring:message code="spring.filtersUnavailableMessage"/>';
    $(function() {
        /*pktb.init({
            ns: '${ns}',
            cr: '<c:url value="/"/>',
            userServiceURL: '/lotus/dd/userService.do',
            currentUser : jQuery.parseJSON('${currentUser}')
        })          */


        $(function() {
            var vars = {
                ns: '${ns}',
                cr: '<c:url value="/"/>',
                cn: '${cn}',
                userServiceURL: '/lotus/dd/userService.do',
                currentUser : jQuery.parseJSON('${currentUser}')
            }
            pktb.init(vars)
            pktbAdminConsole(vars)
        })
    })
</script>