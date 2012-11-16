<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<td class=docs>
<div class="span-5">
	<p><spring:message code="login.credentials"/></p>
	<ul>
		<li>scott/rochester</li>
	</ul>
</div>

<div class="span-10 append-2 last">
	<c:if test="${not empty param.login_error}">
		<div class="error">
			<spring:message code="login.errormessage"/><br /><br />
			<spring:message code="login.error"/>: <%= ((AuthenticationException) session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
		</div>
	</c:if>
	<form name="f" action="<c:url value="/loginProcess" />" method="post">
		<fieldset>
			<legend><spring:message code="login.title"/></legend>
			<p>
				<label for="j_username"><spring:message code="login.username"/>:</label>
				<br />
				<input type="text" name="j_username" id="j_username" <c:if test="${not empty param.login_error}">value="<%= session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>"</c:if> />
			</p>
			<script type="text/javascript">
				Spring.addDecoration(new Spring.ElementDecoration({
					elementId : "j_username",
					widgetType : "dijit.form.ValidationTextBox",
					widgetAttrs : { required : true }}));
			</script>
			<p>
				<label for="j_password"><spring:message code="login.password"/>:</label>
				<br />
				<input type="password" name="j_password" id="j_password" />
			</p>
			<script type="text/javascript">
				Spring.addDecoration(new Spring.ElementDecoration({
					elementId : "j_password",
					widgetType : "dijit.form.ValidationTextBox",
					widgetAttrs : { required : true}}));
			</script>
			<p>
				<input type="checkbox" name="_spring_security_remember_me" id="remember_me" />
				<label for="remember_me"><spring:message code="login.remember"/></label>
			</p>
			<script type="text/javascript">
				Spring.addDecoration(new Spring.ElementDecoration({
					elementId : "remember_me",
					widgetType : "dijit.form.CheckBox"}));
			</script>
			<p>
				<button id="submit" type="submit"><spring:message code="login.submit"/></button>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.ValidateAllDecoration({event : 'onclick', elementId : 'submit'}));
				</script>
			</p>
		</fieldset>
	</form>
</div>
</td><td class="code"></td>
