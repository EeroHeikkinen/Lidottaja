<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<h1>Lisää kenttä</h1>

<c:url var="mappingUrl" value="/mapping"/>
<form:form modelAttribute="fieldMapping" action="${mappingUrl}" method="get" cssClass="inline">
    <span class="errors span-18">
    	<form:errors path="*"/>
    </span>
	<fieldset>
		<div class="span-8">
			<label for="searchString">Kentän nimi:</label>
			<form:input id="fieldNames" path="fieldNames"/>
		</div>
		<div class="span-3 last">
			<button type="submit">Lisää</button>
		</div>		
    </fieldset>
</form:form>