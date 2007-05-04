<%--
- Renders a tree of folders in option tags
- 
-  TODO - move as much of the code into parameters as possible
--%>
<%@ include file="include.jsp" %>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>
<%@ attribute name="depth"      required="true" %>
<%@ attribute name="entries"       required="true" type="java.util.Collection" %>
<%@ attribute name="parentIdPath"  required="true" %>

<c:forEach items="${entries}" var="bookmarkEntry">
    <c:set var="entryIdPath" value="${parentIdPath}.${bookmarkEntry.id}" scope="page"/>
    
    <c:if test="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}">
        <option cssClass="portlet-form-input-field" value="${entryIdPath}"><bm:padding count="${depth * 4}" escapeXml="false" content="&nbsp;"/>${bookmarkEntry.name}</option>
        <bm:folderOptions depth="${depth + 1}" entries="${bookmarkEntry.sortedChildren}" parentIdPath="${entryIdPath}"/>
    </c:if>
</c:forEach>
