<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="localParentFolderIds" value="${parentFolderIds}"/>
<c:set var="localEntries" value="${bookmarkEntries}"/>

<c:forEach items="${localEntries}" var="bookmarkEntry">
    <c:set var="fullEntryId" value="${localParentFolderIds}.${bookmarkEntry.id}" scope="page"/>
    
    <portlet:actionURL var="deleteEntry">
        <portlet:param name="action" value="deleteEntry"/>
        <portlet:param name="entryIndex" value="${fullEntryId}"/>
    </portlet:actionURL>
    
    <c:set var="isFolder" value="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}" scope="page"/>
    <c:choose>
        <c:when test="${isFolder}">
            <portlet:actionURL var="entryUrl">
                <portlet:param name="action" value="toggleFolder"/>
                <portlet:param name="folderIndex" value="${fullEntryId}"/>
            </portlet:actionURL>
            <c:set var="entryTarget" scope="page"></c:set>
            
            <c:choose>
                <c:when test="${bookmarkEntry.minimized}">
                    <c:set var="folderImgSufix" scope="page">closed</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="folderImgSufix" scope="page">opened</c:set>
                </c:otherwise>
            </c:choose>
            <c:set var="entryImg"      value="${pageContext.request.contextPath}/img/folder-${folderImgSufix}.gif" scope="page"/>
            <c:set var="entryImgError" value="" scope="page"/>

            <spring:message code="portlet.entry.folder.desc" arguments="${bookmarkEntry.name}" var="entryDescText" scope="page"/>
            <spring:message code="portlet.entry.folder.edit" arguments="${bookmarkEntry.name}" var="entryEditText" scope="page"/>
            <spring:message code="portlet.entry.folder.delete" arguments="${bookmarkEntry.name}" var="entryDeleteText" scope="page"/>
            
            <c:set var="entryType"     value="folder" scope="page"/>
        </c:when>
        <c:otherwise>
            <c:set var="entryUrl"      value="${bookmarkEntry.url}" scope="page"/>
            <c:choose>
                <c:when test="${bookmarkEntry.newWindow}">
                    <c:set var="entryTarget" scope="page">target="_blank"</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="entryTarget" scope="page"></c:set>
                </c:otherwise>
            </c:choose>
            
            <c:set var="entryImg"      value="${bookmarkEntry.url}/favicon.ico" scope="page"/>
            <c:set var="entryImgError" value="onerror=\"this.src='${pageContext.request.contextPath}/img/bookmark.gif';\"" scope="page"/>

            <spring:message code="portlet.entry.bookmark.desc" arguments="${bookmarkEntry.name}" var="entryDescText" scope="page"/>
            <spring:message code="portlet.entry.bookmark.edit" arguments="${bookmarkEntry.name}" var="entryEditText" scope="page"/>
            <spring:message code="portlet.entry.bookmark.delete" arguments="${bookmarkEntry.name}" var="entryDeleteText" scope="page"/>

            
            <c:set var="entryType"     value="bookmark" scope="page"/>
        </c:otherwise>
    </c:choose>
        
    <li>
        <a id="${portletNamespace}url_${fullEntryId}" href="${entryUrl}" ${entryTarget} title="${bookmarkEntry.noteLines[0]}">
            <img src="${entryImg}" border="0" alt="${entryDesc}" ${entryImgError}/>
            <span id="${portletNamespace}name_${fullEntryId}" class="label">${bookmarkEntry.name}</span>
        </a>
        
        <span name="${portletNamespace}_entryEditSpan" class="hidden">
            <span class="padding"></span>
            
            <a href="#${portletNamespace}_TOP" onclick="editEntry('${entryType}', '${portletNamespace}', '${localParentFolderIds}', '${fullEntryId}');" title="${entryEditText}">
                <img src="${pageContext.request.contextPath}/img/edit.gif" alt="${entryEditText}"/>
            </a>
            
            <a href="#${portletNamespace}_TOP" onclick="return deleteEntry('${entryType}', '${portletNamespace}', '${bookmarkEntry.name}', '${deleteEntry}');" title="${entryDeleteText}">
                <img src="${pageContext.request.contextPath}/img/delete.gif" alt="${entryDeleteText}"/>
            </a>
        </span>
        
        <span id="${portletNamespace}note_${fullEntryId}" class="hidden">${bookmarkEntry.note}</span>
        
        <c:if test="${isFolder && !bookmarkEntry.minimized}">
            <ul class="subBookmarkList">
                <c:set var="parentFolderIds" value="${fullEntryId}" scope="request"/>
                <c:set var="bookmarkEntries" value="${bookmarkEntry.sortedChildren}" scope="request"/>
                <c:import url="renderEntry.jsp"/>
            </ul>
        </c:if>
    </li>
</c:forEach>