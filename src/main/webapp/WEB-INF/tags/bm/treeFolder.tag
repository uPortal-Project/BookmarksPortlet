<%--
- Renders the base of the bookmarks UL/LI tree.
- 
-  TODO - move as much of the code into parameters as possible
--%>
<%@ include file="include.jsp" %>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>
<%@ attribute name="treeName"          required="true" %>
<%@ attribute name="entries"           required="true" type="java.util.Collection" %>
<%@ attribute name="parentIdPath"      required="true" %>
<%@ attribute name="namespace"         required="false" %>
<%@ attribute name="cssClass"          required="false" %>
<%@ attribute name="folderIdSuffix"    required="false" %>
<%@ attribute name="isVFolder"         required="false" %>

    
<ul id="${namespace}${treeName}${folderIdSuffix}_${parentIdPath}" class="${cssClass}">
    <c:choose>
        <c:when test="${isVFolder}">
            <bm:treeEntries treeName="${treeName}" entries="${entries}" parentIdPath="${parentIdPath}" namespace="${namespace}" editable="false"/>
        </c:when>
        <c:otherwise>
            <bm:treeEntries treeName="${treeName}" entries="${entries}" parentIdPath="${parentIdPath}" namespace="${namespace}" editable="true"/>
        </c:otherwise>
    </c:choose>
</ul>
