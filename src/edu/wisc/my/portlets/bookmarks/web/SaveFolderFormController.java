/*******************************************************************************
 * Copyright 2006, The Board of Regents of the University of Wisconsin System.
 * All rights reserved.
 *
 * A non-exclusive worldwide royalty-free license is granted for this Software.
 * Permission to use, copy, modify, and distribute this Software and its
 * documentation, with or without modification, for any purpose is granted
 * provided that such redistribution and use in source and binary forms, with or
 * without modification meets the following conditions:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *c
 * 3. Redistributions of any form whatsoever must retain the following
 * acknowledgement:
 *
 * "This product includes software developed by The Board of Regents of
 * the University of Wisconsin System."
 *
 *THIS SOFTWARE IS PROVIDED BY THE BOARD OF REGENTS OF THE UNIVERSITY OF
 *WISCONSIN SYSTEM "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING,
 *BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE BOARD OF REGENTS OF
 *THE UNIVERSITY OF WISCONSIN SYSTEM BE LIABLE FOR ANY DIRECT, INDIRECT,
 *INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 *OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package edu.wisc.my.portlets.bookmarks.web;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.support.FolderUtils;
import edu.wisc.my.portlets.bookmarks.domain.support.IndexPathInfo;



/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class SaveFolderFormController extends ViewBookmarksController {
//TODO check for loop here, create bind exception if there is one
    /**
     * @see org.springframework.web.portlet.mvc.SimpleFormController#onSubmitAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
        final String folderPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);
        final String entryIndex = StringUtils.defaultIfEmpty(request.getParameter("indexPath"), null);
        
        //User edited bookmark
        final Folder newFolder = (Folder)command;
        
        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request);
        
        //Parent folder specified in the form
        final IndexPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, folderPath);
        final Folder targetParentFolder = (Folder)targetParentPathInfo.getTarget();
        final List<Entry> targetParentChildren = targetParentFolder.getChildren();
        
        
        //Editing an existing bookmark
        if (entryIndex != null) {
            final IndexPathInfo originalFolderPathInfo = FolderUtils.getEntryInfo(bs, entryIndex);
            final Folder originalParent = originalFolderPathInfo.getParent();
            final List<Entry> originalChildren = originalParent.getChildren();
            
            //TODO deal with an invalid target type (null or Bookmark)
            final Folder originalFolder = (Folder)originalFolderPathInfo.getTarget();
            
            final boolean wouldCreateLoop = FolderUtils.deepContains(originalFolder, targetParentFolder);
            if (wouldCreateLoop) {
                //TODO figure out how to get the error data to display on the result form
                return;
            }
            
            newFolder.setId(originalFolder.getId());
            newFolder.setChildren(originalFolder.getChildren());
            newFolder.setMinimized(originalFolder.isMinimized());
            newFolder.setCreated(originalFolder.getCreated());
            newFolder.setModified(new Date());
            
            //Always remove the bookmark from the list
            final int[] indexPath = originalFolderPathInfo.getIndexPath();
            originalChildren.remove(indexPath[indexPath.length - 1]);
        }

        //Add the bookmark to the target folder, re-sort the folder
        targetParentChildren.add(newFolder);
        Collections.sort(targetParentChildren);
        
        //Persist the changes to the BookmarkSet 
        this.bookmarkStore.storeBookmarkSet(bs);
    }
}
