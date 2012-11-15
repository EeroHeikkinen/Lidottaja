<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<td class="docs">
<h1>Uusi arvo</h1>

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
				<div>
					<div class="span-4">
						<label>Tietosisältö:</label>
					</div>
					<div class="span-7 last">
						<p>${field.name}</p>
					</div>
				</div>
				<div>
					<div class="span-4">
						<label>Ohje:</label>
					</div>
					<div class="span-7 last">
						<p>${field.description}</p>
					</div>
				</div>
				<div>
					<div class="span-4">
						<label for="name">Kentän nimi:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="field"/></p>
					</div>
				</div>
				
				<div>
					<div class="span-4">
						<label for="example">Esimerkki:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="value"/></p>
					</div>
				</div>
				<div>
					<p>
					<button type="submit" id="save" name="_eventId_save">OK</button>
					<button type="submit" name="_eventId_cancel" >Peruuta</button>
					</p>
				</div>
			</fieldset>
		</form:form>	
	</div>
</div>
</td><td class="code"></td>