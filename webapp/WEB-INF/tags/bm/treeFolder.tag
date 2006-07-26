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

    
<ul id="${namespace}${treeName}${folderIdSuffix}_${parentIdPath}" class="${cssClass}">
    <bm:treeEntries treeName="${treeName}" entries="${entries}" parentIdPath="${parentIdPath}" namespace="${namespace}"/>
</ul>
