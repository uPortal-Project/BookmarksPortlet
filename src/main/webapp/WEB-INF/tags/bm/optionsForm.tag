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
    <c:set var="formClass" value="bookmark-hide" scope="page"/>
</c:if>

<portlet:actionURL portletMode="EDIT" var="formUrl"/>
<form:form id="${namespace}${formName}" name="${namespace}${formName}" method="post" action="${formUrl}" commandName="${commandName}" cssClass="${formClass}">
    <h3><spring:message code="portlet.edit.mode" text="Edit Mode"/></h3>
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
