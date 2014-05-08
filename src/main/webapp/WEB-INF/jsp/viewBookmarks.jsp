<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="bookmarksPortlet">
    <div id="${portletNamespace}bookmarksTreeAndForm">
        <c:set var="bookmarkEntries" value="${bookmarkSet.sortedChildren}" scope="page"/>
        <c:choose>
            <c:when test="${fn:length(bookmarkEntries) > 0}">
                <bm:treeFolder treeName="bookmarks" folderIdSuffix="RootEntry" entries="${bookmarkEntries}" parentIdPath="${bookmarkSet.id}" namespace="${portletNamespace}" cssClass="bookmarkList"/>
                    <script type="text/javascript">
                        hideEditMode("${portletNamespace}");
                    </script>
                <br/>
            </c:when>
            <c:otherwise>
                <div class="portlet-font">
                    <spring:message code="portlet.view.noBookmarks"/>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
