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
<c:set var="portletNamespace" scope="request"><portlet:namespace/></c:set>
<c:set var="hasErrors" scope="request" value="${errors.errorCount > 0}"/>

<div class="bookmarksPortlet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookmarks.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/script/bookmarks.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        new BookmarksPortletData(   "${portletNamespace}",      //namespace
                                    "newWindow",                //bookmark_form_newWindow
                                    "url",                      //bookmark_form_url
                                    "emptyBookmarkForm",        //bookmark_forms_empty
                                    "errorBookmarkForm",        //bookmark_forms_error
                                    "url_",                     //bookmark_reference_url
                                    "bookmarksTreeAndForm",     //bookmarksTreeAndForm
                                    "bookmarksChildFolder_",    //entry_childFolderPrefix
                                    "entryEditButtons",         //entry_edit_buttons
                                    "cancelLink",               //entry_edit_cancelLink
                                    "editLink",                 //entry_edit_editLine
                                    "action",                   //entry_form_action
                                    "editBookmark",             //entry_form_action_editBookmar
                                    "editFolder",               //entry_form_action_editFolder
                                    "editCollection",           //entry_form_action_editCollection
                                    "newBookmark",              //entry_form_action_newBookmark
                                    "newFolder",                //entry_form_action_newFolder
                                    "newCollection",            //entry_form_action_newCollection
                                    "folderPath",               //entry_form_folderPath
                                    "folderActionLabel",        //entry_form_folderPathLabel
                                    "idPath",                   //entry_form_idPath
                                    "name",                     //entry_form_name
                                    "note",                     //entry_form_note
                                    "entryImg_",                //entry_imagePrefix
                                    "referenceFolderPath",      //entry_reference_folderPath
                                    "name_",                    //entry_reference_name
                                    "note_",                    //entry_reference_note
                                    "emptyFolderForm",          //folder_forms_empty
                                    "errorFolderForm",          //folder_forms_error
                                    "/img/folder-closed.gif",   //folder_image_closed
                                    "/img/folder-opened.gif",   //folder_image_open
                                    "url",                      //collection_form_url
                                    "emptyCollectionForm",      //collection_forms_empty
                                    "errorCollectionForm",      //collection_forms_error
                                    "optionsForm",         //options_form
                                    "optionsLink",              //options_showLink
                                    "<spring:message code="portlet.script.folder.create" javaScriptEscape="true"/>",                    //messages_folder_create
                                    "<spring:message code="portlet.script.folder.move" javaScriptEscape="true"/>",                      //messages_folder_move
                                    "<spring:message code="portlet.script.delete.confirm.bookmark.prefix" javaScriptEscape="true"/>",   //messages_delete_bookmark_prefix
                                    "<spring:message code="portlet.script.delete.confirm.bookmark.suffix" javaScriptEscape="true"/>",   //messages_delete_bookmark_suffix
                                    "<spring:message code="portlet.script.delete.confirm.collection.prefix" javaScriptEscape="true"/>",     //messages_delete_collection_prefix
                                    "<spring:message code="portlet.script.delete.confirm.collection.suffix" javaScriptEscape="true"/>",    //messages_delete_collection_suffix
                                    "<spring:message code="portlet.script.delete.confirm.folder.prefix" javaScriptEscape="true"/>",     //messages_delete_folder_prefix
                                    "<spring:message code="portlet.script.delete.confirm.folder.suffix" javaScriptEscape="true"/>");    //messages_delete_folder_suffix
    </script>


    <c:set var="optionsFormHidden" value="true"/>
    <c:if test="${hasErrors && empty folderCommand && empty bookmarkCommand}">
        <c:set var="optionsFormHidden" value="false"/>
        <c:set var="optionsLinkClass" value="hidden" scope="page"/>
        <c:set var="bookmarksTreeAndFormClass" value="hidden" scope="page"/>
    </c:if>
    
    <div style="float: right; ${ guestMode ? 'display: none;' : '' }">
        <a id="${portletNamespace}optionsLink" href="javascript:void(0);" class="${optionsLinkClass}" onclick="showOptionsForm('${portletNamespace}');return false;"><spring:message code="portlet.view.options"/></a>
    </div>
    <bm:optionsForm formName="optionsForm" commandName="options" hidden="${optionsFormHidden}" namespace="${portletNamespace}"/>
    

    <div id="${portletNamespace}bookmarksTreeAndForm" class="${bookmarksTreeAndFormClass}">
        <c:set var="bookmarkEntries" value="${bookmarkSet.sortedChildren}" scope="page"/>
        <c:choose>
            <c:when test="${fn:length(bookmarkEntries) > 0}">
                <bm:treeFolder treeName="bookmarks" folderIdSuffix="RootEntry" entries="${bookmarkEntries}" parentIdPath="${bookmarkSet.id}" namespace="${portletNamespace}" cssClass="bookmarkList"/>
                <br/>
            </c:when>
            <c:otherwise>
                <div class="portlet-font">
                    <spring:message code="portlet.view.noBookmarks"/>
                </div>
            </c:otherwise>
        </c:choose>
        
        <c:if test="${!guestMode }">
	        <a href="javascript:void(0);" onclick="newBookmark('${portletNamespace}');return false;" class="jsTextLink portlet-form-label" style="${ guestMode ? 'display: none;' : '' }"><spring:message code="portlet.view.addBookmark"/></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="javascript:void(0);" onclick="newFolder('${portletNamespace}');return false;" class="jsTextLink portlet-form-label" style="${ guestMode ? 'display: none;' : '' }"><spring:message code="portlet.view.addFolder"/></a>
	        &nbsp;&nbsp;&nbsp;
	        <a href="javascript:void(0);" onclick="newCollection('${portletNamespace}');return false;" class="jsTextLink portlet-form-label" style="${ fn:length(availableCollections) > 0 and !guestMode ? '' : 'display: none;' }"><spring:message code="portlet.view.addCollection"/></a>
	        <c:if test="${fn:length(bookmarkEntries) > 0}">
	            &nbsp;&nbsp;&nbsp;    
	            <a href="javascript:void(0);" id="${portletNamespace}editLink" onclick="toggleEditMode('${portletNamespace}', true);return false;" class="jsTextLink portlet-form-label"><spring:message code="portlet.view.edit.show"/></a>
	            <a href="javascript:void(0);" id="${portletNamespace}cancelLink" onclick="toggleEditMode('${portletNamespace}', false);return false;" class="jsTextLink portlet-form-label hidden"><spring:message code="portlet.view.edit.hide"/></a>
	        </c:if>
        </c:if>
        
        <c:if test="${hasErrors}">
            <c:choose>
                <c:when test="${fn:startsWith(param['action'], 'new')}">
                    <spring:message code="portlet.script.folder.create" javaScriptEscape="false" var="folderActionLabel"/>
                </c:when>
                <c:when test="${fn:startsWith(param['action'], 'edit')}">
                    <spring:message code="portlet.script.folder.move" javaScriptEscape="false" var="folderActionLabel"/>
                </c:when>
            </c:choose>
        </c:if>
        
        <c:if test="${hasErrors && !empty bookmarkCommand}">
            <bm:bookmarkForm 
                formName="errorBookmarkForm" commandName="bookmarkCommand" entries="${bookmarkEntries}" 
                hidden="false" namespace="${portletNamespace}" actionInput="${param['action']}" 
                idPathInput="${param['idPath']}" folderActionLabel="${folderActionLabel}" isErrorForm="true"/>

            <script type="text/javascript">
                setupErrorForm("${portletNamespace}", "errorBookmarkForm", "${param['folderPath']}");
            </script>
        </c:if>
        <bm:bookmarkForm formName="emptyBookmarkForm" commandName="emptyBookmarkCommand" entries="${bookmarkEntries}" hidden="true" namespace="${portletNamespace}"/> 
        
        <c:if test="${hasErrors && !empty folderCommand}">
            <bm:folderForm 
                formName="errorFolderForm" commandName="folderCommand" entries="${bookmarkEntries}" 
                hidden="false" namespace="${portletNamespace}" actionInput="${param['action']}" 
                idPathInput="${param['idPath']}" folderActionLabel="${folderActionLabel}" isErrorForm="true"/>
            
            <script type="text/javascript">
                setupErrorForm("${portletNamespace}", "errorFolderForm", "${param['folderPath']}");
            </script>
        </c:if>
        <bm:folderForm formName="emptyFolderForm" commandName="emptyFolderCommand" entries="${bookmarkEntries}" hidden="true" namespace="${portletNamespace}"/>

        <c:if test="${hasErrors && !empty collectionCommand}">
            <bm:collectionForm 
                formName="errorCollectionForm" commandName="collectionCommand" entries="${bookmarkEntries}" 
                hidden="false" namespace="${portletNamespace}" actionInput="${param['action']}" 
                idPathInput="${param['idPath']}" folderActionLabel="${folderActionLabel}" isErrorForm="true"/>

            <script type="text/javascript">
                setupErrorForm("${portletNamespace}", "errorCollectionForm", "${param['folderPath']}");
            </script>
        </c:if>
        <bm:collectionForm formName="emptyCollectionForm" commandName="emptyCollectionCommand" entries="${bookmarkEntries}" hidden="true" namespace="${portletNamespace}"/> 
    </div>
</div>