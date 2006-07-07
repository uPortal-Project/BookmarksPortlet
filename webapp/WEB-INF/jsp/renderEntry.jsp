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
            <c:choose>
                <c:when test="${preferences.saveFolderState}">
                    <portlet:actionURL var="entryUrl">
                        <portlet:param name="action" value="toggleFolder"/>
                        <portlet:param name="folderIndex" value="${fullEntryId}"/>
                    </portlet:actionURL>
                </c:when>
                <c:otherwise>
                    <c:set var="entryUrl"    value="#" scope="page"/>
                    <c:set var="entryUrlOnClick" scope="page">onclick="toggleFolder('${pageContext.request.contextPath}', '${portletNamespace}', '${fullEntryId}');return false;"</c:set>
                </c:otherwise>
            </c:choose>

            <c:set var="entryTarget"  value="" scope="page"/>
            
            <c:choose>
                <c:when test="${preferences.saveFolderState && bookmarkEntry.minimized}">
                    <c:set var="folderImgSufix" scope="page">closed</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="folderImgSufix" scope="page">opened</c:set>
                </c:otherwise>
            </c:choose>
            <c:set var="entryImg"      value="${pageContext.request.contextPath}/img/folder-${folderImgSufix}.gif" scope="page"/>

            <spring:message code="portlet.entry.folder.desc" arguments="${bookmarkEntry.name}" var="entryDescText" scope="page"/>
            <spring:message code="portlet.entry.folder.edit" arguments="${bookmarkEntry.name}" var="entryEditText" scope="page"/>
            <spring:message code="portlet.entry.folder.delete" arguments="${bookmarkEntry.name}" var="entryDeleteText" scope="page"/>
            
            <c:set var="entryType"     value="folder" scope="page"/>
        </c:when>
        <c:otherwise>
            <c:set var="entryUrl"        value="${bookmarkEntry.url}" scope="page"/>
            <c:set var="entryUrlOnClick" value="" scope="page"/>
            <c:choose>
                <c:when test="${bookmarkEntry.newWindow}">
                    <c:set var="entryTarget" scope="page">target="_blank"</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="entryTarget" value="" scope="page"/>
                </c:otherwise>
            </c:choose>
            <c:set var="entryImg"      value="${pageContext.request.contextPath}/img/bookmark.gif" scope="page"/>

            <spring:message code="portlet.entry.bookmark.desc" arguments="${bookmarkEntry.name}" var="entryDescText" scope="page"/>
            <spring:message code="portlet.entry.bookmark.edit" arguments="${bookmarkEntry.name}" var="entryEditText" scope="page"/>
            <spring:message code="portlet.entry.bookmark.delete" arguments="${bookmarkEntry.name}" var="entryDeleteText" scope="page"/>
            
            <c:set var="entryType"     value="bookmark" scope="page"/>
        </c:otherwise>
    </c:choose>
        
    <li class="bookmarkListItem">
        <a id="${portletNamespace}url_${fullEntryId}" 
            href="${entryUrl}" ${entryUrlOnClick} ${entryTarget} 
            title="${bookmarkEntry.noteLines[0]}"><img id="${portletNamespace}entryImg_${fullEntryId}" src="${entryImg}" border="0" alt="${entryDesc}"/>
            <span id="${portletNamespace}name_${fullEntryId}" class="portlet-font">${bookmarkEntry.name}</span></a>
        
        <span class="padding"></span>
        
        <%-- Need both ID (for IE) and NAME (for FF/Opera) --%>
        <span id="${portletNamespace}_entryEditUI" name="${portletNamespace}_entryEditUI" class="hidden">
            <a href="#" onclick="editEntry('${entryType}', '${portletNamespace}', '${localParentFolderIds}', '${fullEntryId}');return false;"
                title="${entryEditText}"><img src="${pageContext.request.contextPath}/img/edit.gif" alt="${entryEditText}"/></a>
            
            <a href="#" onclick="deleteEntry('${entryType}', '${portletNamespace}', '${bookmarkEntry.name}', '${deleteEntry}');return false;" 
                title="${entryDeleteText}"><img src="${pageContext.request.contextPath}/img/delete.gif" alt="${entryDeleteText}"/></a>
        </span>

        <span id="${portletNamespace}note_${fullEntryId}" class="hidden">${bookmarkEntry.note}</span>
        
        <c:if test="${isFolder}">
            <c:choose>
                <c:when test="${preferences.saveFolderState && bookmarkEntry.minimized}">
                    <c:set var="childrenHiddenClass" value="hidden" scope="page"/>
                </c:when>
                <c:otherwise>
                    <c:set var="childrenHiddenClass" value="" scope="page"/>
                </c:otherwise>
            </c:choose>
            
            <c:if test="${!preferences.saveFolderState || (preferences.saveFolderState && !bookmarkEntry.minimized)}">
                <ul class="subBookmarkList ${childrenHiddenClass}" id="${portletNamespace}children_${fullEntryId}">
                    <c:set var="parentFolderIds" value="${fullEntryId}" scope="request"/>
                    <c:set var="bookmarkEntries" value="${bookmarkEntry.sortedChildren}" scope="request"/>
                    <c:import url="renderEntry.jsp"/>
                </ul>
            </c:if>
        </c:if>
    </li>
</c:forEach>