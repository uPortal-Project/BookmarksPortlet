<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="localDepth" value="${depth}"/>
<c:set var="localParentFolderIds" value="${parentFolderIds}"/>
<c:set var="localEntries" value="${bookmarkEntries}"/>

<c:forEach items="${localEntries}" var="bookmarkEntry">
    <c:set var="fullEntryId" value="${localParentFolderIds}.${bookmarkEntry.id}" scope="page"/>
    
    <c:if test="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}">
        <option value="${fullEntryId}"><c:forEach begin="0" end="${localDepth}"><c:out escapeXml="false" value="&nbsp;&nbsp;&nbsp;&nbsp;"/></c:forEach>${bookmarkEntry.name}</option>
    
        <c:set var="depth" value="${depth + 1}" scope="request"/>
        <c:set var="parentFolderIds" value="${fullEntryId}" scope="request"/>
        <c:set var="bookmarkEntries" value="${bookmarkEntry.sortedChildren}" scope="request"/>
        <c:import url="renderFolder.jsp"/>
    </c:if>
</c:forEach>
