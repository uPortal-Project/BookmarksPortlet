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
