<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="portletNamespace" scope="request"><portlet:namespace/></c:set>
        
<div>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookmarks.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/script/bookmarks.js" type="text/javascript"></script>
    
    <c:set var="sortedChildren" value="${bookmarkSet.sortedChildren}"/>
    <c:choose>
        <c:when test="${fn:length(sortedChildren) > 0}">
            <div>
                <ul class="bookmarkList">
                    <c:set var="parentFolderIds" value="${bookmarkSet.id}" scope="request"/>
                    <c:set var="bookmarkEntries" value="${sortedChildren}" scope="request"/>
                    <c:import url="renderEntry.jsp"/>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                <spring:message code="portlet.view.noBookmarks"/>
            </div>
        </c:otherwise>
    </c:choose>
    <br/>

    <a href="#" onclick="newEntry('bookmark', '${portletNamespace}')"><spring:message code="portlet.view.addBookmark"/></a>
    <a href="#" onclick="newEntry('folder', '${portletNamespace}')"><spring:message code="portlet.view.addFolder"/></a>
    
    <portlet:actionURL var="formUrl"/>
    <form:form name="${portletNamespace}bookmarksForm" method="POST" action="${formUrl}" commandName="emptyCommand">
        <form:errors path="*"/>
        
        <div id="${portletNamespace}bookmarksDiv" class="hidden">
            <br/>
            
            <input name="action" type="hidden"/>
            <input name="indexPath" type="hidden"/>
            <input name="type" type="hidden"/>

            <!-- todo form:errors tag -->            
            
            <table padding="0">
                <tr>
                    <td align="right"><spring:message code="portlet.form.name"/></td>
                    <td><form:input path="name" cssStyle="width: 250px;"/></td>
                </tr>
                <tr id="${portletNamespace}urlRow">
                    <td align="right"><spring:message code="portlet.form.url"/></td>
                    <td><form:input path="url" cssStyle="width: 250px;"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top"><spring:message code="portlet.form.note"/></td>
                    <td><form:textarea path="note" cssStyle="width: 250px;"></form:textarea></td>
                </tr>
                
                <c:set var="folderRowClass" value="hidden" scope="page"/>
                <c:forEach items="${sortedChildren}" var="bookmarkEntry">
                    <c:if test="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}">
                        <c:set var="folderRowClass" value=" " scope="page"/>
                    </c:if>
                </c:forEach>
                        
                <tr class="${folderRowClass}">
                    <td id="${portletNamespace}folderAction" align="right" valign="top"><spring:message code="portlet.form.folder"/></td>
                    <td>
                        <select name="folderPath" style="width: 250px;">
                            <option selected="true" value="-1"><spring:message code="portlet.form.folder.none"/></option>
                            
                            <c:set var="depth" value="0" scope="request"/>
                            <c:set var="parentFolderIds" value="${bookmarkSet.id}" scope="request"/>
                            <c:set var="bookmarkEntries" value="${sortedChildren}" scope="request"/>
                            <c:import url="renderFolder.jsp"/>
                        </select>
                    </td>
                </tr>
                <tr id="${portletNamespace}newWindowRow">
                    <td/>
                    <td>
                        <form:checkbox id="newWindow" path="newWindow"/>
                        <label for="newWindow"><spring:message code="portlet.form.newWindow"/></label>
                    </td>
                </tr>
                <tr>
                    <td align="right" colspan="2"">
                        <spring:message code="portlet.form.save" var="portletFormSave"/>
                        <input value="${portletFormSave}" type="submit"/>
                        <spring:message code="portlet.form.cancel" var="portletFormCancel"/>
                        <input value="${portletFormCancel}" type="reset" onclick="cancelEntry('${portletNamespace}');"/>
                    </td>
                </tr>
            </table>
        </div>
    </form:form>
</div>
