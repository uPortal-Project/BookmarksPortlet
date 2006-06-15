<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="portletNamespace" scope="request"><portlet:namespace/></c:set>
        
<div>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookmarks.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/script/bookmarks.js" type="text/javascript"></script>
    
    <div>
        <ul class="bookmarkList">
            <c:set var="parentFolderIndexes" value="-1" scope="request"/>
            <c:set var="bookmarkEntries" value="${bookmarkSet.children}" scope="request"/>
            <c:import url="renderEntry.jsp"/>
        </ul>
    </div>
    <br/>
    <a href="#" onclick="newEntry('bookmark', '${portletNamespace}')">Add Bookmark</a>
    <a href="#" onclick="newEntry('folder', '${portletNamespace}')">Add Folder</a>
    
    <br/>
    <br/>
    
    <portlet:actionURL var="formUrl"/>
    <form:form name="${portletNamespace}bookmarksForm" method="POST" action="${formUrl}" commandName="emptyCommand">
        <form:errors path="*"/>
        
        <div id="${portletNamespace}bookmarksDiv" class="hidden">
            <input name="action" type="hidden"/>
            <input name="indexPath" type="hidden"/>
            <input name="type" type="hidden"/>

            <!-- todo form:errors tag -->            
            
            <table padding="0">
                <tr>
                    <td align="right">Name:</td>
                    <td><form:input path="name" cssStyle="width: 250px;"/></td>
                </tr>
                <tr id="${portletNamespace}urlRow">
                    <td align="right">Url:</td>
                    <td><form:input path="url" cssStyle="width: 250px;"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top">Note:</td>
                    <td><form:textarea path="note" cssStyle="width: 250px;"></form:textarea></td>
                </tr>
                <tr>
                    <td align="right" valign="top">Folder:</td>
                    <td>
                        <select name="folderPath" style="width: 250px;">
                            <option selected="true" value="-1">None</option>
                            
                            <c:set var="depth" value="0" scope="request"/>
                            <c:set var="parentFolderIndexes" value="-1" scope="request"/>
                            <c:set var="bookmarkEntries" value="${bookmarkSet.children}" scope="request"/>
                            <c:import url="renderFolder.jsp"/>
                        </select>
                    </td>
                </tr>
                <tr id="${portletNamespace}newWindowRow">
                    <td/>
                    <td>
                        <form:checkbox id="newWindow" path="newWindow"/>
                        <label for="newWindow">Open in new window</label>
                    </td>
                </tr>
                <tr>
                    <td align="right" colspan="2"">
                        <input value="Cancel" type="reset" onclick="cancelEntry('${portletNamespace}');"/>
                        <input value="Save" type="submit"/>
                    </td>
                </tr>
            </table>
        </div>
    </form:form>
</div>
