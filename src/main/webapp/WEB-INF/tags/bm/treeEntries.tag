<%--

    Licensed to Apereo under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Apereo licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%--
- Renders a list of entries in LI tags
- 
-  TODO - move as much of the code into parameters as possible
--%>
<%@ include file="include.jsp" %>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>
<%@ attribute name="treeName"      required="true" %>
<%@ attribute name="entries"       required="true" type="java.util.Collection" %>
<%@ attribute name="parentIdPath"  required="true" %>
<%@ attribute name="namespace"     required="false" %>
<%@ attribute name="editable"      required="false" %>

<c:forEach items="${entries}" var="bookmarkEntry">
    <c:set var="entryIdPath" value="${parentIdPath}.${bookmarkEntry.id}"/>

    <%-- Need to zero out page scoped parameters since they seem to be scoped to more than just the .tag file --%>
    <c:set var="entryUrlOnClick"/>
    <c:set var="folderImgSufix"/>
    <c:set var="isFolderClosed"/>
    <c:set var="entryTarget"/>
    <c:set var="childrenHiddenClass"/>
    
    <c:set var="isFolder" value="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}"/>
    <c:set var="isVFolder" value="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.CollectionFolder')}"/>
    <c:choose>
        <c:when test="${isFolder}">
            <c:choose>
                <c:when test="${options.defaultFolderOperation == 'SAVED'}">
                    <portlet:actionURL var="entryUrl">
                        <portlet:param name="action" value="toggleFolder"/>
                        <portlet:param name="folderIndex" value="${entryIdPath}"/>
                    </portlet:actionURL>
                </c:when>
                <c:otherwise>
                    <c:set var="entryUrl"    value="javascript:void(0);"/>
                    <c:set var="entryUrlOnClick">onclick="toggleFolder('${namespace}', '${entryIdPath}', '${pageContext.request.contextPath}');return false;"</c:set>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${options.defaultFolderOperation == 'CLOSED' || (options.defaultFolderOperation == 'SAVED' && bookmarkEntry.minimized)}">
                    <c:set var="isFolderClosed">true</c:set>
                    <c:set var="folderImgSufix">closed</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="isFolderClosed">false</c:set>
                    <c:set var="folderImgSufix">opened</c:set>
                </c:otherwise>
            </c:choose>
            <c:set var="entryImg"      value="${pageContext.request.contextPath}/img/folder-${folderImgSufix}.gif"/>

            <spring:message code="portlet.entry.folder.desc" arguments="${bookmarkEntry.name}" var="entryDescText"/>
            <spring:message code="portlet.entry.folder.edit" arguments="${bookmarkEntry.name}" var="entryEditText"/>
            <spring:message code="portlet.entry.folder.delete" arguments="${bookmarkEntry.name}" var="entryDeleteText"/>
            
            <c:set var="entryType"     value="folder"/>
        </c:when>
        <c:when test="${ isVFolder }">
            <c:choose>
                <c:when test="${options.defaultFolderOperation == 'SAVED'}">
                    <portlet:actionURL var="entryUrl">
                        <portlet:param name="action" value="toggleFolder"/>
                        <portlet:param name="folderIndex" value="${entryIdPath}"/>
                    </portlet:actionURL>
                </c:when>
                <c:otherwise>
                    <c:set var="entryUrl"    value="javascript:void(0);"/>
                    <c:set var="entryUrlOnClick">onclick="toggleFolder('${namespace}', '${entryIdPath}', '${pageContext.request.contextPath}');return false;"</c:set>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${options.defaultFolderOperation == 'CLOSED' || (options.defaultFolderOperation == 'SAVED' && bookmarkEntry.minimized)}">
                    <c:set var="isFolderClosed">true</c:set>
                    <c:set var="folderImgSufix">closed</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="isFolderClosed">false</c:set>
                    <c:set var="folderImgSufix">opened</c:set>
                </c:otherwise>
            </c:choose>
            <c:set var="entryImg"      value="${pageContext.request.contextPath}/img/folder-${folderImgSufix}.gif"/>

            <spring:message code="portlet.entry.folder.desc" arguments="${bookmarkEntry.name}" var="entryDescText"/>
            <spring:message code="portlet.entry.folder.edit" arguments="${bookmarkEntry.name}" var="entryEditText"/>
            <spring:message code="portlet.entry.folder.delete" arguments="${bookmarkEntry.name}" var="entryDeleteText"/>
            
            <c:set var="entryType"     value="collection"/>
        </c:when>
        <c:otherwise>
            <c:set var="entryUrl"        value="${bookmarkEntry.url}"/>
            <c:if test="${uwfn:isState(renderRequest.windowState, 'EXCLUSIVE') || bookmarkEntry.newWindow}">
                <c:set var="entryTarget" >target="_blank"</c:set>
            </c:if>
            <c:set var="entryImg"      value="${pageContext.request.contextPath}/img/bookmark.gif"/>

            <spring:message code="portlet.entry.bookmark.desc" arguments="${bookmarkEntry.name}" var="entryDescText"/>
            <spring:message code="portlet.entry.bookmark.edit" arguments="${bookmarkEntry.name}" var="entryEditText"/>
            <spring:message code="portlet.entry.bookmark.delete" arguments="${bookmarkEntry.name}" var="entryDeleteText"/>
            
            <c:set var="entryType"     value="bookmark"/>
        </c:otherwise>
    </c:choose>
        
    <li id="${namespace}${treeName}TreeItem" class="bookmarkListItem">
        <c:set var="cssClass">portlet-font ${(isFolder || isVFolder) ? 'bookmarksFolder' : 'bookmarksEntry'}</c:set>
        <a id="${namespace}url_${entryIdPath}" 
            href="${entryUrl}" ${entryUrlOnClick} ${entryTarget} 
            title="${bookmarkEntry.noteLines[0]}"><img id="${namespace}entryImg_${entryIdPath}" src="${entryImg}" border="0" alt="${entryDescText}"/>
            <span id="${namespace}name_${entryIdPath}" class="${cssClass}">${bookmarkEntry.name}</span></a>
        
        <span class="padding"></span>
        
        <%-- Need both ID (for IE) and NAME (for FF/Opera) --%>
        <c:if test="${not (editable == false) }">
	        <span id="${namespace}entryEditButtons" name="${namespace}entryEditButtons" >
	            <a href="javascript:void(0);" onclick="editEntry('${namespace}', '${entryType}', '${parentIdPath}', '${entryIdPath}');return false;"
	                title="${entryEditText}"><img src="${pageContext.request.contextPath}/img/edit.gif" alt="${entryEditText}"/></a>

	            <form action="<portlet:actionURL escapeXml="false"></portlet:actionURL>" method="post" style="display: inline-block" onSubmit="return deleteEntry('${namespace}', '${entryType}', '${entryIdPath}');">
	                <input type="hidden" name="action" value="deleteEntry"/>
	                <input type="hidden" name="entryIndex" value="${entryIdPath}"/>
	                <button type="submit" class="btn btn-link btn-unpadded">
	                    <img src="${pageContext.request.contextPath}/img/delete.gif" alt="${entryDeleteText}"/>
	                </button>
	            </form>
	        </span>
        </c:if>

        <span id="${namespace}note_${entryIdPath}" class="bookmark-hide">${bookmarkEntry.note}</span>
        
        <c:if test="${(isFolder || isVFolder ) && !(options.defaultFolderOperation == 'SAVED' && bookmarkEntry.minimized)}">
            <c:if test="${isFolderClosed}">
                <c:set var="childrenHiddenClass" value="bookmark-hide"/>
            </c:if>
            
            <c:choose>
                <c:when test="${ isVFolder }">
                    <bm:treeFolder treeName="${treeName}" folderIdSuffix="ChildFolder" entries="${bookmarkEntry.sortedChildren}" parentIdPath="${entryIdPath}" namespace="${portletNamespace}" cssClass="subBookmarkList ${childrenHiddenClass}" isVFolder="true"/>
                </c:when>
                <c:otherwise>
                    <bm:treeFolder treeName="${treeName}" folderIdSuffix="ChildFolder" entries="${bookmarkEntry.sortedChildren}" parentIdPath="${entryIdPath}" namespace="${portletNamespace}" cssClass="subBookmarkList ${childrenHiddenClass}"/>
                </c:otherwise>
            </c:choose>
        </c:if>
    </li>
</c:forEach>
