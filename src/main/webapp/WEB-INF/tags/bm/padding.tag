<%@ include file="include.jsp" %>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>
<%@ attribute name="content"   required="true" %>
<%@ attribute name="count"     required="true" %>
<%@ attribute name="escapeXml" required="false" %>
<c:forEach begin="1" end="${count}"><c:out escapeXml="${escapeXml}" value="${content}"/></c:forEach>