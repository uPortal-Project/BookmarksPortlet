<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="localParentFolderIndexes" value="${parentFolderIndexes}"/>
<c:set var="localEntries" value="${bookmarkEntries}"/>

<c:forEach items="${localEntries}" var="bookmarkEntry" varStatus="entryStatus">
    <c:set var="fullEntryIndex" value="${localParentFolderIndexes}.${entryStatus.index}" scope="page"/>
    
    <portlet:actionURL var="deleteEntry">
        <portlet:param name="action" value="deleteEntry"/>
        <portlet:param name="entryIndex" value="${fullEntryIndex}"/>
    </portlet:actionURL>
    
    <c:set var="isFolder" value="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}" scope="page"/>
    <c:choose>
        <c:when test="${isFolder}">
            <portlet:actionURL var="entryUrl">
                <portlet:param name="action" value="toggleFolder"/>
                <portlet:param name="folderIndex" value="${fullEntryIndex}"/>
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
            <c:set var="entryDesc"     value="Folder" scope="page"/>
            
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
            <c:set var="entryDesc"     value="Bookmark" scope="page"/>
            
            <c:set var="entryType"     value="bookmark" scope="page"/>
        </c:otherwise>
    </c:choose>
        
    <li>
        <a id="<portlet:namespace/>url_${fullEntryIndex}" href="${entryUrl}" ${entryTarget}>
            <img src="${entryImg}" border="0" alt="${entryDesc}" ${entryImgError}/>
            <span id="<portlet:namespace/>name_${fullEntryIndex}" class="label">${bookmarkEntry.name}</span>
        </a>
        <span class="padding"></span>
        <a href="#" onclick="editEntry('${entryType}', '<portlet:namespace/>', '${localParentFolderIndexes}', '${fullEntryIndex}');"><img src="${pageContext.request.contextPath}/img/edit.gif"/></a>
        <a href="${deleteEntry}"><img src="${pageContext.request.contextPath}/img/delete.gif"/></a>
        
        <span id="<portlet:namespace/>note_${fullEntryIndex}" class="hidden">${bookmarkEntry.note}</span>
        
        <c:if test="${isFolder && !bookmarkEntry.minimized}">
            <ul class="subBookmarkList">
                <c:set var="parentFolderIndexes" value="${fullEntryIndex}" scope="request"/>
                <c:set var="bookmarkEntries" value="${bookmarkEntry.children}" scope="request"/>
                <c:import url="renderEntry.jsp"/>
            </ul>
        </c:if>
    </li>
</c:forEach>