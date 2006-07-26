<%--
- Displays the Options form for the specified options Command object.
- 
-  TODO - move as much of the code into parameters as possible
--%>
<%@ include file="include.jsp" %>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>
<%@ attribute name="commandName"   required="true" %>
<%@ attribute name="formName"      required="true" %>
<%@ attribute name="hidden"        required="false" %>
<%@ attribute name="namespace"     required="false" %>
    
<c:if test="${hidden}">
    <c:set var="formClass" value="hidden" scope="page"/>
</c:if>

<portlet:actionURL var="formUrl"/>
<form:form name="${namespace}${formName}" method="POST" action="${formUrl}" commandName="${commandName}" cssClass="${formClass}">
    <input name="action" type="hidden" value="saveOptions"/>

    <table padding="0">
        <tr>
            <td/>
            <td>
                <form:checkbox id="saveFolderState" path="saveFolderState" cssClass="portlet-form-field"/>
                <label class="portlet-form-field-label" for="saveFolderState"><spring:message code="portlet.options.form.saveState"/></label>
            </td>
            <td><form:errors cssClass="portlet-msg-error" path="saveFolderState"/></td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <spring:message code="portlet.options.form.save" var="portletFormSave"/>
                <input class="portlet-form-button" value="${portletFormSave}" type="submit"/>
                <spring:message code="portlet.options.form.cancel" var="portletFormCancel"/>
                <input class="portlet-form-button" value="${portletFormCancel}" type="reset" onclick="cancelOptionsForm('${namespace}', '${formName}');"/>
            </td>
            <td/>
        </tr>
    </table>
</form:form>
