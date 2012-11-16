<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<td class="docs"> 
<form:form action="map" method="post" cssClass="inline">
	<h1><spring:message code="intro.welcome"/></h1>
	<p>
	 	<spring:message code="intro.general"/>
	</p>
	<hr/> 
	<p>
		<spring:message code="intro.steps"/>
	</p>
	<ul>
		<li><spring:message code="intro.step1"/></li>
		<li><spring:message code="intro.step2"/></li>
		<li><spring:message code="intro.step3"/></li>
	</ul>
	<p>
		<button type="submit" id="proceed" name="_eventId_proceed"><spring:message code="intro.begin"/></button>
	</p>
</form:form>
</td>
<td class="code"></td> 