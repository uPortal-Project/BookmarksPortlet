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
            <td width="1%">&nbsp;</td>
            <td width="1%">&nbsp;</td>
            <td width="1%">&nbsp;</td>
            <td width="97%">&nbsp;</td>
        </tr>
        <tr>
            <td class="portlet-form-field-label" colspan="4">
                <strong><spring:message code="portlet.options.form.defaultFolderOperations.title"/></strong>
            </td>
        </tr>
        <c:forEach items="${options.defaultFolderOperations}" var="operation">
            <tr>
                <td/>
                <td valign="top"><form:radiobutton id="defaultFolderOperation_${operation}" path="defaultFolderOperation" value="${operation}"/></td>
                <td colspan="2">
                    <label class="portlet-form-field-label" for="defaultFolderOperation_${operation}">
                        <strong><spring:message code="portlet.options.form.defaultFolderOperations.${operation}.title"/></strong>
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="3"/>
                <td class="portlet-form-field-label">
                    <spring:message code="portlet.options.form.defaultFolderOperations.${operation}.description"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td align="right" colspan="4">
                <spring:message code="portlet.options.form.save" var="portletFormSave"/>
                <input class="portlet-form-button" value="${portletFormSave}" type="submit"/>
                <spring:message code="portlet.options.form.cancel" var="portletFormCancel"/>
                <input class="portlet-form-button" value="${portletFormCancel}" type="reset" onclick="cancelOptionsForm('${namespace}', '${formName}');"/>
            </td>
        </tr>
    </table>
</form:form>
