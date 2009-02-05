<%--
- Displays the Folder form for the specified options Command object.
- 
-  TODO - move as much of the code into parameters as possible
--%>
<%@ include file="include.jsp" %>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>
<%@ attribute name="commandName"   required="true" %>
<%@ attribute name="formName"      required="true" %>
<%@ attribute name="entries"       required="true" type="java.util.Collection" %>
<%@ attribute name="hidden"        required="false" %>
<%@ attribute name="namespace"     required="false" %>
<%@ attribute name="actionInput"       required="false" %>
<%@ attribute name="idPathInput"       required="false" %>
<%@ attribute name="folderActionLabel" required="false" %>
<%@ attribute name="isErrorForm"       required="false" %>
    
<%--
For the errors
    setting the action, idPath, & type
    setting the folder action label
    populating the folder options
--%>
    
<c:if test="${hidden}">
    <c:set var="formClass" value="hidden" scope="page"/>
</c:if>
<c:if test="${empty folderActionLabel}">
    <c:set var="folderActionLabel"><spring:message code="portlet.entry.form.folder"/></c:set>
</c:if>

<portlet:actionURL var="formUrl"/>
<form:form id="${namespace}${formName}" name="${namespace}${formName}" method="post" action="${formUrl}" commandName="${commandName}" cssClass="${formClass}">
    <input name="action" type="hidden" value="${actionInput}"/>
    <input name="idPath" type="hidden" value="${idPathInput}"/>

    <table padding="0">
        <c:if test="${isErrorForm}">
            <tr>
                <td colspan="3"><span class="portlet-msg-error"><spring:message code="portlet.entry.form.error.banner"/></span></td>
            </tr>
        </c:if>
        <tr>
            <td align="right"><label for="name" class="portlet-form-field-label"><spring:message code="portlet.entry.form.name"/></label></td>
            <td><form:input path="name" cssStyle="width: 250px;" cssClass="portlet-form-input-field"/></td>
            <td><form:errors cssClass="portlet-msg-error" path="name"/></td>
        </tr>
        <tr>
            <td align="right" valign="top"><label for="note" class="portlet-form-field-label"><spring:message code="portlet.entry.form.note"/></label></td>
            <td><form:textarea path="note" cssStyle="width: 250px;" cssClass="portlet-form-input-field"></form:textarea></td>
            <td><form:errors cssClass="portlet-msg-error" path="note"/></td>
        </tr>
        
        <%-- Check to see if a folder exists in the list of children so the folder drop down is only displayed when a folder is availabe --%>
        <c:set var="folderRowClass" value="hidden" scope="page"/>
        <c:forEach items="${entries}" var="entry">
            <c:if test="${uwfn:instanceOf(entry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}">
                <c:set var="folderRowClass" value="" scope="page"/>
            </c:if>
        </c:forEach>
        <tr class="${folderRowClass}">
            <td align="right" valign="top" class="portlet-form-field-label" id="${namespace}${formName}folderActionLabel"><label for="folderPath">${folderActionLabel}</label></td>
            <td>
                <select name="folderPath" id="folderPath" style="width: 250px;">
                </select>
                
								<label for="referenceFolderPath" class="hidden">Reference Folder Path:</label>
                <select name="referenceFolderPath" id="referenceFolderPath" class="hidden" disabled="true">
                    <option cssClass="portlet-form-input-field" value="${bookmarkSet.id}"><spring:message code="portlet.entry.form.folder.none"/></option>
                    <bm:folderOptions depth="0" entries="${entries}" parentIdPath="${bookmarkSet.id}"/>
                </select>
            </td>
        </tr>
        
        <tr>
            <td align="right" colspan="2">
                <spring:message code="portlet.entry.form.save" var="portletFormSave"/>
                <input class="portlet-form-button" value="${portletFormSave}" type="submit"/>
                <spring:message code="portlet.entry.form.cancel" var="portletFormCancel"/>
                <input class="portlet-form-button" value="${portletFormCancel}" type="reset" onclick="cancelFolder('${namespace}', '${formName}');"/>
            </td>
        </tr>
    </table>
</form:form>
