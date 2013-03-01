<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<td class="docs">
<h1>Luo uusi mappaus-profiili</h1>

<form:form modelAttribute="mapping" action="${flowExecutionUrl}" method="post" cssClass="inline">
    <span class="errors span-18">
    	<form:errors path="*"/>
    </span>
	<fieldset>
		<div class="span-8">
			<label for="searchString">Nimi:</label>
			<form:input id="name" path="name"/>
		</div>
		<div class="span-3 last">
			<button type="submit" id="proceed" name="_eventId_proceed">Jatka</button>
			<button type="submit" name="_eventId_cancel" >Peruuta</button>
		</div>		
    </fieldset>
    <c:if test="${not empty mappings}">
    <h1>Tai muokkaa jotain aikaisemmin luomistasi:</h1>
	    <fieldset>
	    	<c:forEach items="${mappings}" var="mapping" varStatus="loop">
	    		<a href="${flowExecutionUrl}&_eventId_modifyMapping&mappingIndex=${ loop.index }">${ mapping.name }</a>
	    	</c:forEach> 
	    </fieldset>
    </c:if>
</form:form>
</td>
<td class="code"></td>