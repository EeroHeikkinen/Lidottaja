<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %> 

<td class="docs">

<h1>Mappaus: ${mapping.name}</h1>

<h2>Valitse LIDO-elementti</h2>

<div id="bookingForm">
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
				<div>
					<p>
<!-- 						<button type="submit" id="previous" name="_eventId_previous">Vaihda nimeä</button>-->
						<button type="submit" id="proceed" name="_eventId_proceed">Tallenna</button>
						<button type="submit" name="_eventId_cancel" >Aloita alusta</button>
					</p>
				</div>
			</fieldset>
		</form:form>
</div>

</td>
<td class="code">
<pre class="prettyprint"><tag:printStack stack="${mapping.binder}"/></pre>
</td>

<tr>
<td></td><td class="code"><iframe style="width:100%; height: 600px;" src="../preview/${mapping.name}"></iframe></td>