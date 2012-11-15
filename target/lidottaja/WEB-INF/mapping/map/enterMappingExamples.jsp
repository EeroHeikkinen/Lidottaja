<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<td class="code">
<h1>Anna esimerkkejä</h1>

<div id="bookingForm">
	<div class="span-12">
		<spring:hasBindErrors name="mapping">
			<div class="error">
				<spring:bind path="mapping.*">
					<c:forEach items="${status.errorMessages}" var="error">
						<span><c:out value="${error}"/></span><br>
					</c:forEach>
				</spring:bind>
			</div>
		</spring:hasBindErrors>
		<form:form modelAttribute="mapping" action="${flowExecutionUrl}">
			<fieldset>
				<c:forEach var="field" items="${mapping.fields}" varStatus="counter">
				${field.name} 
				<form:input path="fields[${counter.index}].example"/><br/>
				</c:forEach>
			</fieldset>
			<fieldset>
				<div>
					<p>
					<button type="submit" id="previous" name="_eventId_previous">Edellinen</button>
					<button type="submit" id="proceed" name="_eventId_proceed">Seuraava</button>
					<button type="submit" name="_eventId_cancel" >Peruuta</button>
					</p>
				</div>
			</fieldset>
		</form:form>	
	</div>
</div>
</td><td class="code"></td>