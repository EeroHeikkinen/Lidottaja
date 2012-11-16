<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title>Lido-tukipaketti</title>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<link rel="stylesheet" href="<c:url value="/resources/styles/docco.css" />" type="text/css" media="screen" />
<link rel="stylesheet" href="<c:url value="/resources/styles/local.css" />" type="text/css" media="screen" />
<link href="<c:url value="/resources/styles/prettify.css"/>" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<c:url value="/resources/styles/prettify.js"/>"></script>
</head>
<body onload="prettyPrint()">
<div id="topbar">
	<p>
		<security:authorize ifAllGranted="ROLE_USER">
			<c:if test="${pageContext.request.userPrincipal != null}">
				<spring:message code="user.welcome"/>, ${pageContext.request.userPrincipal.name} |
			</c:if>
			<a href="<c:url value="/logout" />">Logout</a>
		</security:authorize>
		<security:authorize ifAllGranted="ROLE_ANONYMOUS">
			<a href="<c:url value="/login" />">Login</a>
		</security:authorize>
	</p>
</div>
<div id="container">
<div id="background"></div>
<table cellpadding="0" cellspacing="0" style="min-width: 1350px">
<tbody>
<tr id="section-1">
	<tiles:insertAttribute name="body" />
</tr>
</tbody>
</table>
</div>
</body>
</html>