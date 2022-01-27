/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.web;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.CollapsibleEntry;
import edu.wisc.my.portlets.bookmarks.domain.CollectionFolder;
import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.support.FolderUtils;
import edu.wisc.my.portlets.bookmarks.domain.support.IdPathInfo;
import edu.wisc.my.portlets.bookmarks.domain.validation.BookmarkValidator;
import edu.wisc.my.portlets.bookmarks.domain.validation.CollectionValidator;
import edu.wisc.my.portlets.bookmarks.domain.validation.FolderValidator;
import edu.wisc.my.portlets.bookmarks.web.support.BookmarkSetRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.ViewConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.annotation.Resource;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * Controller resolves the BookmarkSet owner and name for the request and displays it
 * as the model via Edit mode.
 *
 * @author Unknown
 * @version $Id: $Id
 */
@Controller
@RequestMapping("EDIT")
public class EditBookmarksController {

    private static final Logger logger = LoggerFactory.getLogger(EditBookmarksController.class);

    @Autowired
    private BookmarkSetRequestResolver bookmarkSetRequestResolver;
    @Resource(name = "bookmarkStore")
    private BookmarkStore bookmarkStore;
    @Autowired
    private ReferenceData referenceData;

    @InitBinder("boookmark")
    public void initBookmarkBinder(WebDataBinder binder) {
        binder.setValidator(new BookmarkValidator());
    }

    @InitBinder("folder")
    public void initFolderBinder(WebDataBinder binder) {
        binder.setValidator(new FolderValidator());
    }

    @InitBinder("collection")
    public void initCollectionBinder(WebDataBinder binder) {
        binder.setValidator(new CollectionValidator());
    }

    @RenderMapping
    public String addDataForView(Model model, RenderRequest request) {
        logger.debug("Entering EDIT addDataForView()");
        BindingResult errors = (BindingResult) request.getAttribute(ViewConstants.ERRORS);
        final Map<String, Object> refData = referenceData.getRefData(request, errors);
        model.addAllAttributes(refData);
        return "editBookmarks";
    }

    private boolean foundErrors(ActionRequest request, ActionResponse response, BindingResult result) {
        // don't persist for guest users
        if (request.getRemoteUser() == null) {
            logger.warn("Guest user attempting to edit bookmarks");
            response.setRenderParameter("action", "viewBookmarks");
            return true;
        } else if (result.hasErrors()) {
            logger.info("User {} experiencing {} errors", request.getRemoteUser(), result.getErrorCount());
            final String action = request.getParameter("action");
            final String idPath = request.getParameter("idPath");
            final String folderPath = request.getParameter("folderPath");

            response.setRenderParameter("action", action);
            response.setRenderParameter("idPath", idPath);
            response.setRenderParameter("folderPath", folderPath);
            request.setAttribute(ViewConstants.ERRORS, result);
            return true;
        } else {
            logger.debug("No errors for user {}", request.getRemoteUser());
            response.setRenderParameter("action", "viewBookmarks");
            return false;
        }
    }

    @ActionMapping(params = "action=newBookmark")
    public void newBookmark(@Valid @ModelAttribute("bookmark") Bookmark bookmark, BindingResult result,
                            ActionRequest request, ActionResponse response) {
        if (foundErrors(request, response, result)) {
            return;
        }

        final String targetParentPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request);

        //Ensure the created & modified dates are set correctly
        bookmark.setCreated(new Date());
        bookmark.setModified(bookmark.getCreated());

        final Folder targetParent;
        if (targetParentPath != null) {
            //Get the target parent folder
            final IdPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, targetParentPath);
            if (targetParentPathInfo == null || targetParentPathInfo.getTarget() == null) {
                throw new IllegalArgumentException("The specified parent Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetParentPath + "'");
            }

            targetParent = (Folder)targetParentPathInfo.getTarget();
        } else {
            targetParent = bs;
        }

        final Map<Long, Entry> targetChildren = targetParent.getChildren();

        //Add the new bookmark to the target parent
        targetChildren.put(bookmark.getId(), bookmark);

        //Persist the changes to the BookmarkSet
        this.bookmarkStore.storeBookmarkSet(bs);
    }

    @ActionMapping(params = "action=editBookmark")
    public void editBookmark(@Valid @ModelAttribute("bookmark") Bookmark bookmark, BindingResult result,
                             ActionRequest request, ActionResponse response) {
        if (foundErrors(request, response, result)) {
            return;
        }

        final String targetParentPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);
        final String targetEntryPath = StringUtils.defaultIfEmpty(request.getParameter("idPath"), null);

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        if (bs == null) {
            throw new IllegalArgumentException("No BookmarkSet exists for request='" + request + "'");
        }

        //Get the target parent folder
        final IdPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, targetParentPath);
        if (targetParentPathInfo == null || targetParentPathInfo.getTarget() == null) {
            throw new IllegalArgumentException("The specified parent Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetParentPath + "'");
        }

        final Folder targetParent = (Folder)targetParentPathInfo.getTarget();
        final Map<Long, Entry> targetChildren = targetParent.getChildren();

        //Get the original bookmark & it's parent folder
        final IdPathInfo originalBookmarkPathInfo = FolderUtils.getEntryInfo(bs, targetEntryPath);
        if (originalBookmarkPathInfo.getTarget() == null) {
            throw new IllegalArgumentException("The specified Bookmark does not exist. BaseFolder='" + bs + "' and idPath='" + targetEntryPath + "'");
        }

        final Folder originalParent = originalBookmarkPathInfo.getParent();
        final Bookmark originalBookmark = (Bookmark)originalBookmarkPathInfo.getTarget();

        //If moving the bookmark
        if (targetParent.getId() != originalParent.getId()) {
            final Map<Long, Entry> originalChildren = originalParent.getChildren();
            originalChildren.remove(originalBookmark.getId());

            bookmark.setCreated(originalBookmark.getCreated());
            bookmark.setModified(new Date());

            targetChildren.put(bookmark.getId(), bookmark);
        }
        //If just updating the bookmark
        //TODO should the formBackingObject be smarter on form submits for editBookmark and return the targeted bookmark?
        else {
            originalBookmark.setModified(new Date());
            originalBookmark.setName(bookmark.getName());
            originalBookmark.setNote(bookmark.getNote());
            originalBookmark.setUrl(bookmark.getUrl());
            originalBookmark.setNewWindow(bookmark.isNewWindow());
        }

        //Persist the changes to the BookmarkSet
        this.bookmarkStore.storeBookmarkSet(bs);
    }

    @ActionMapping(params = "action=newFolder")
    public void newFolder(@Valid @ModelAttribute("folder") Folder folder, BindingResult result,
                          ActionRequest request, ActionResponse response) {
        if (foundErrors(request, response, result)) {
            return;
        }

        final String targetParentPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request);

        //Ensure the created & modified dates are set correctly
        folder.setCreated(new Date());
        folder.setModified(folder.getCreated());

        final Folder targetParent;
        if (targetParentPath != null) {
            //Get the target parent folder
            final IdPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, targetParentPath);
            if (targetParentPathInfo == null || targetParentPathInfo.getTarget() == null) {
                throw new IllegalArgumentException("The specified parent Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetParentPath + "'");
            }

            targetParent = (Folder)targetParentPathInfo.getTarget();
        }
        else {
            targetParent = bs;
        }

        final Map<Long, Entry> targetChildren = targetParent.getChildren();

        //Add the new bookmark to the target parent
        targetChildren.put(folder.getId(), folder);

        //Persist the changes to the BookmarkSet
        this.bookmarkStore.storeBookmarkSet(bs);
    }

    @ActionMapping(params = "action=editFolder")
    public void editFolder(@Valid @ModelAttribute("folder") Folder folder, BindingResult result,
                           ActionRequest request, ActionResponse response) {
        if (foundErrors(request, response, result)) {
            return;
        }

        final String targetParentPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);
        final String targetEntryPath = StringUtils.defaultIfEmpty(request.getParameter("idPath"), null);

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        if (bs == null) {
            throw new IllegalArgumentException("No BookmarkSet exists for request='" + request + "'");
        }

        //Get the target parent folder
        final IdPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, targetParentPath);
        if (targetParentPathInfo == null || targetParentPathInfo.getTarget() == null) {
            throw new IllegalArgumentException("The specified parent Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetParentPath + "'");
        }

        final Folder targetParent = (Folder)targetParentPathInfo.getTarget();
        final Map<Long, Entry> targetChildren = targetParent.getChildren();

        //Get the original bookmark & it's parent folder
        final IdPathInfo originalBookmarkPathInfo = FolderUtils.getEntryInfo(bs, targetEntryPath);
        if (originalBookmarkPathInfo.getTarget() == null) {
            throw new IllegalArgumentException("The specified Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetEntryPath + "'");
        }

        final Folder originalParent = originalBookmarkPathInfo.getParent();
        final Folder originalFolder = (Folder)originalBookmarkPathInfo.getTarget();

        //If moving the bookmark
        if (targetParent.getId() != originalParent.getId()) {
            final Map<Long, Entry> originalChildren = originalParent.getChildren();
            originalChildren.remove(originalFolder.getId());

            folder.setCreated(originalFolder.getCreated());
            folder.setModified(new Date());
            folder.setChildren(originalFolder.getChildren());

            //Hibernate doesn't let us move already persisted objects, need to clone the tree to allow for the object to be re-added
            final Folder clonedCommandFolder = FolderUtils.deepCloneFolder(folder, false);

            targetChildren.put(clonedCommandFolder.getId(), clonedCommandFolder);
        }
        //If just updating the bookmark
        //TODO should the formBackingObject be smarter on form submits for editBookmark and return the targeted bookmark?
        else {
            originalFolder.setModified(new Date());
            originalFolder.setName(folder.getName());
            originalFolder.setNote(folder.getNote());
        }

        //Persist the changes to the BookmarkSet
        this.bookmarkStore.storeBookmarkSet(bs);
    }

    @ActionMapping(params = "action=newCollection")
    public void newCollection(@Valid @ModelAttribute("collection") CollectionFolder collection, BindingResult result,
                              ActionRequest request, ActionResponse response) {
        if (foundErrors(request, response, result)) {
            return;
        }

        final String targetParentPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);

        //User edited bookmark

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request);

        //Ensure the created & modified dates are set correctly
        collection.setCreated(new Date());
        collection.setModified(collection.getCreated());

        final Folder targetParent;
        if (targetParentPath != null) {
            //Get the target parent folder
            final IdPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, targetParentPath);
            if (targetParentPathInfo == null || targetParentPathInfo.getTarget() == null) {
                throw new IllegalArgumentException("The specified parent Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetParentPath + "'");
            }

            targetParent = (Folder)targetParentPathInfo.getTarget();
        }
        else {
            targetParent = bs;
        }

        final Map<Long, Entry> targetChildren = targetParent.getChildren();

        //Add the new bookmark to the target parent
        targetChildren.put(collection.getId(), collection);

        //Persist the changes to the BookmarkSet
        this.bookmarkStore.storeBookmarkSet(bs);
    }

    @ActionMapping(params = "action=editCollection")
    public void editCollection(@Valid @ModelAttribute("collection") CollectionFolder collection, BindingResult result,
                               ActionRequest request, ActionResponse response) {
        if (foundErrors(request, response, result)) {
            return;
        }

        final String targetParentPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);
        final String targetEntryPath = StringUtils.defaultIfEmpty(request.getParameter("idPath"), null);

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        if (bs == null) {
            throw new IllegalArgumentException("No BookmarkSet exists for request='" + request + "'");
        }

        //Get the target parent folder
        final IdPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, targetParentPath);
        if (targetParentPathInfo == null || targetParentPathInfo.getTarget() == null) {
            throw new IllegalArgumentException("The specified parent Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetParentPath + "'");
        }

        final Folder targetParent = (Folder)targetParentPathInfo.getTarget();
        final Map<Long, Entry> targetChildren = targetParent.getChildren();

        //Get the original bookmark & it's parent folder
        final IdPathInfo originalBookmarkPathInfo = FolderUtils.getEntryInfo(bs, targetEntryPath);
        if (originalBookmarkPathInfo.getTarget() == null) {
            throw new IllegalArgumentException("The specified Bookmark does not exist. BaseFolder='" + bs + "' and idPath='" + targetEntryPath + "'");
        }

        final Folder originalParent = originalBookmarkPathInfo.getParent();
        final CollectionFolder originalCollection = (CollectionFolder)originalBookmarkPathInfo.getTarget();

        //If moving the bookmark
        if (targetParent.getId() != originalParent.getId()) {
            final Map<Long, Entry> originalChildren = originalParent.getChildren();
            originalChildren.remove(originalCollection.getId());

            collection.setCreated(originalCollection.getCreated());
            collection.setModified(new Date());

            targetChildren.put(collection.getId(), collection);
        }
        //If just updating the bookmark
        //TODO should the formBackingObject be smarter on form submits for editBookmark and return the targeted bookmark?
        else {
            originalCollection.setModified(new Date());
            originalCollection.setName(collection.getName());
            originalCollection.setNote(collection.getNote());
            originalCollection.setUrl(collection.getUrl());
            originalCollection.setMinimized(collection.isMinimized());
        }

        //Persist the changes to the BookmarkSet
        this.bookmarkStore.storeBookmarkSet(bs);
    }

    @ActionMapping(params = "action=toggleFolder")
    public void toggleFolder(ActionRequest request, ActionResponse response) {
        final String folderIndex = StringUtils.defaultIfEmpty(request.getParameter("folderIndex"), null);

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        if (bs == null) {
            throw new IllegalArgumentException("No BookmarkSet exists for request='" + request + "'");
        }

        final IdPathInfo targetFolderPathInfo = FolderUtils.getEntryInfo(bs, folderIndex);
        if (targetFolderPathInfo != null && targetFolderPathInfo.getTarget() != null) {
            final CollapsibleEntry targetFolder = (CollapsibleEntry)targetFolderPathInfo.getTarget();
            targetFolder.setMinimized(!targetFolder.isMinimized());

            //Persist the changes to the BookmarkSet
            this.bookmarkStore.storeBookmarkSet(bs);
        }
        else {
            logger.warn("No IdPathInfo found for BaseFolder='" + bs + "' and idPath='" + folderIndex + "'");
        }

        //Go back to view bookmarks
        response.setRenderParameter("action", "viewBookmarks");

    }

    @ActionMapping(params = "action=deleteEntry")
    public void deleteEntry(ActionRequest request, ActionResponse response) {
        if (request.getRemoteUser() == null) {
            return;
        }

        final String entryIndex = StringUtils.defaultIfEmpty(request.getParameter("entryIndex"), null);

        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        if (bs == null) {
            throw new IllegalArgumentException("No BookmarkSet exists for request='" + request + "'");
        }

        final IdPathInfo targetEntryPathInfo = FolderUtils.getEntryInfo(bs, entryIndex);
        if (targetEntryPathInfo != null && targetEntryPathInfo.getTarget() != null) {
            final Folder parentFolder = targetEntryPathInfo.getParent();
            if (parentFolder != null) {
                final Map<Long, Entry> children = parentFolder.getChildren();
                final Entry target = targetEntryPathInfo.getTarget();
                children.remove(target.getId());

                //Persist the changes to the BookmarkSet
                this.bookmarkStore.storeBookmarkSet(bs);
            }
            else {
                //Deleting the root bookmark
                this.bookmarkStore.removeBookmarkSet(bs.getOwner(), bs.getName());
            }
        }
        else {
            logger.warn("No IdPathInfo found for BaseFolder='" + bs + "' and idPath='" + entryIndex + "'");
        }

        //Go back to view bookmarks
        response.setRenderParameter("action", "viewBookmarks");
    }
}
