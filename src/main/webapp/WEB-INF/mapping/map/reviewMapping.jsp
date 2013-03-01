<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<td class="docs">
<h1>Onneksi olkoon, mappauksesi on luotu!</h1>

<form:form modelAttribute="mapping" action="${flowExecutionUrl}" method="post" cssClass="inline">
    <span class="errors span-18">
    	<form:errors path="*"/>
    </span>
	<fieldset>
		<legend>Lataa muodossa</legend>
		<div class="span-8" style="font-size: 30pt; line-height: 39pt">  
			<a href="/LidoMapper/src/${mapping.name}.xml">XML</a> <a href="/LidoMapper/docs/${mapping.name}.html">HTML</a> <!-- <a href="/LidoMapper/zip/">ZIP</a>-->
		</div>
		<div class="span-3 last">
			<button type="submit" id="ok" name="_eventId_ok">OK</button>
		</div>		
    </fieldset>
</form:form>
</td>
<td class="code"></td>