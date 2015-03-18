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
- Renders a tree of folders in option tags
- 
-  TODO - move as much of the code into parameters as possible
--%>
<%@ include file="include.jsp" %>
<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>
<%@ attribute name="depth"      required="true" %>
<%@ attribute name="entries"       required="true" type="java.util.Collection" %>
<%@ attribute name="parentIdPath"  required="true" %>

<c:forEach items="${entries}" var="bookmarkEntry">
    <c:set var="entryIdPath" value="${parentIdPath}.${bookmarkEntry.id}" scope="page"/>
    
    <c:if test="${uwfn:instanceOf(bookmarkEntry, 'edu.wisc.my.portlets.bookmarks.domain.Folder')}">
        <option cssClass="portlet-form-input-field" value="${entryIdPath}"><bm:padding count="${depth * 4}" escapeXml="false" content="&nbsp;"/>${bookmarkEntry.name}</option>
        <bm:folderOptions depth="${depth + 1}" entries="${bookmarkEntry.sortedChildren}" parentIdPath="${entryIdPath}"/>
    </c:if>
</c:forEach>
