<%@ tag description="render a binding block" pageEncoding="UTF-8"%><%@ 
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><%@ taglib tagdir="/WEB-INF/tags" prefix="tag"  
%><%@ taglib prefix="m15" uri="http://www.nba.fi/museo2015"
%><%@ attribute name="stack" required="true" type="fi.museo2015.lidottaja.model.Chunk"
%><c:set var="temp" value="one
two"/><c:set var="newline" value="${fn:substring(temp,3,4)}"
/><c:forEach items="${stack.stack}" varStatus="stackStatus" var="item"
><c:choose
><c:when test="${item.hasStack == true}"
><span class="bindingGroup"
>${lastIndent}<tag:printStack stack="${item}"
/></span><c:if test="${item.isMapped == true}"><!-- indent1 start-->${lastIndent}<!--indent stop--><a 
href="${flowExecutionUrl}&_eventId_duplicate&bindingBlock=${item.id}">(+)</a><a 
href="${flowExecutionUrl}&_eventId_delete&bindingBlock=${item.id}">(-)</a>
<!-- indent2 start-->${lastIndent}<!--indent stop--></c:if
></c:when><c:when test="${item.isBindable == true}"><c:choose
><c:when test="${empty item.value}"
>${lastIndent}<span class="bindingPoint"><a 
href="${flowExecutionUrl}&_eventId_createField&bindingPoint=${item.id}">${item.name}</a></span></c:when
><c:otherwise></pre></tr><tr
><td class="docs"><div class="arrow"></div
><p>${item.field}</p></td><td class="code"
><pre class="prettyprint lang-xml">${lastIndent}<a 
href="${flowExecutionUrl}&_eventId_createField&bindingPoint=${item.id}"
><span class="example">${item.value }</span></a
></c:otherwise></c:choose></c:when
><c:otherwise><!-- indent3 start-->${lastIndent}<!--indent stop--><c:set var="lastIndent" value="${m15:extractLastLine(item)}"/><!-- block without last line start--><c:out value="${m15:withoutLastLine(item)}"/><!-- block without ll end --></c:otherwise></c:choose
></c:forEach>