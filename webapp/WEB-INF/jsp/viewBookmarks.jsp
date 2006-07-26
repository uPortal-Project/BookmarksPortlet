<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:set var="portletNamespace" scope="request"><portlet:namespace/></c:set>
<c:set var="hasErrors" scope="request" value="${errors.errorCount > 0}"/>

<div id="bookmarksPortlet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookmarks.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/script/bookmarks.js" type="text/javascript"></script>
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
                                    "newBookmark",              //entry_form_action_newBookmark
                                    "newFolder",                //entry_form_action_newFolder
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
                                    "optionsForm",         //options_form
                                    "optionsLink",              //options_showLink
                                    "<spring:message code="portlet.script.folder.create" javaScriptEscape="true"/>",                    //messages_folder_create
                                    "<spring:message code="portlet.script.folder.move" javaScriptEscape="true"/>",                      //messages_folder_move
                                    "<spring:message code="portlet.script.delete.confirm.bookmark.prefix" javaScriptEscape="true"/>",   //messages_delete_bookmark_prefix
                                    "<spring:message code="portlet.script.delete.confirm.bookmark.suffix" javaScriptEscape="true"/>",   //messages_delete_bookmark_suffix
                                    "<spring:message code="portlet.script.delete.confirm.folder.prefix" javaScriptEscape="true"/>",     //messages_delete_folder_prefix
                                    "<spring:message code="portlet.script.delete.confirm.folder.suffix" javaScriptEscape="true"/>");    //messages_delete_folder_suffix
    </script>


    <c:set var="optionsFormHidden" value="true"/>
    <c:if test="${hasErrors && empty folderCommand && empty bookmarkCommand}">
        <c:set var="optionsFormHidden" value="false"/>
        <c:set var="optionsLinkClass" value="hidden" scope="page"/>
        <c:set var="bookmarksTreeAndFormClass" value="hidden" scope="page"/>
    </c:if>
    
    <div style="float: right;">
        <a id="${portletNamespace}optionsLink" href="#" class="${optionsLinkClass}" onclick="showOptionsForm('${portletNamespace}');return false;"><spring:message code="portlet.view.options"/></a>
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
        
        <a href="#" onclick="newBookmark('${portletNamespace}');return false;" class="jsTextLink portlet-form-label"><spring:message code="portlet.view.addBookmark"/></a>
        &nbsp;&nbsp;&nbsp;
        <a href="#" onclick="newFolder('${portletNamespace}');return false;" class="jsTextLink portlet-form-label"><spring:message code="portlet.view.addFolder"/></a>
        <c:if test="${fn:length(bookmarkEntries) > 0}">
            &nbsp;&nbsp;&nbsp;    
            <a href="#" id="${portletNamespace}editLink" onclick="toggleEditMode('${portletNamespace}', true);return false;" class="jsTextLink portlet-form-label"><spring:message code="portlet.view.edit.show"/></a>
            <a href="#" id="${portletNamespace}cancelLink" onclick="toggleEditMode('${portletNamespace}', false);return false;" class="jsTextLink portlet-form-label hidden"><spring:message code="portlet.view.edit.hide"/></a>
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
    </div>
    
    <portlet:renderURL windowState="EXCLUSIVE" var="exclusiveUrl"/>
    <a href="${exclusiveUrl}">Exclusive</a>
    
    <portlet:renderURL windowState="NORMAL" var="normalUrl"/>
    <a href="${normalUrl}">Normal</a>
</div>