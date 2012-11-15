<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<td class="docs">
<h1>Anna profiilin kentät</h1>

<div id="bookingForm">
	<div class="span-12">
		<spring:hasBindErrors name="field">
			<div class="error">
				<spring:bind path="field.*">
					<c:forEach items="${status.errorMessages}" var="error">
						<span><c:out value="${error}"/></span><br>
					</c:forEach>
				</spring:bind>
			</div>
		</spring:hasBindErrors>
		<form:form modelAttribute="field" action="${flowExecutionUrl}">
			<fieldset>
				<div class="span-8">
					<label for="searchString">Kentän nimi:</label>
					<form:input id="name" path="name"/>
				</div>
				<div class="span-3 last">
					<button type="submit" id="addField" name="_eventId_addField">Lisää</button>
				</div>		
		    </fieldset>
			<fieldset>
				<div><c:forEach var="field" items="${mapping.fields}">
				 ${field.name} <a href="${flowExecutionUrl}&_eventId_removeField&name=${field.name}">Poista</a><br/>		
				</c:forEach>
				</div>
				<div>
					<p>
					<button type="submit" id="proceed" name="_eventId_proceed">Proceed</button>
					<button type="submit" name="_eventId_cancel" >Cancel</button>
					</p>
				</div>
			</fieldset>
		</form:form>	
	</div>
</div>
</td><td class="code"></td>