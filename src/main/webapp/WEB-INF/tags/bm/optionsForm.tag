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
<h3>Options</h3>
<form:form id="${namespace}${formName}" name="${namespace}${formName}" method="post" action="${formUrl}" commandName="${commandName}" cssClass="${formClass}">
	<input name="action" type="hidden" value="saveOptions"/>
	<fieldset>
		<legend><spring:message code="portlet.options.form.defaultFolderOperations.title"/></legend>
		<c:forEach items="${options.defaultFolderOperations}" var="operation">
			<form:radiobutton id="defaultFolderOperation_${operation}" path="defaultFolderOperation" value="${operation}"/>
			<label class="portlet-form-field-label" for="defaultFolderOperation_${operation}">
					<spring:message code="portlet.options.form.defaultFolderOperations.${operation}.title"/>
			</label><br/>
			<p><spring:message code="portlet.options.form.defaultFolderOperations.${operation}.description"/></p>
		</c:forEach>
	</fieldset>
	<spring:message code="portlet.options.form.save" var="portletFormSave"/>
	<input class="portlet-form-button" value="${portletFormSave}" type="submit"/>
	<spring:message code="portlet.options.form.cancel" var="portletFormCancel"/>
	<input class="portlet-form-button" value="${portletFormCancel}" type="reset" onclick="cancelOptionsForm('${namespace}', '${formName}');"/>
</form:form>
