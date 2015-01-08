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
package edu.wisc.my.portlets.bookmarks.web;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.portlet.mvc.AbstractController;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.support.FolderUtils;
import edu.wisc.my.portlets.bookmarks.domain.support.IdPathInfo;
import edu.wisc.my.portlets.bookmarks.web.support.BookmarkSetRequestResolver;



/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12175 $
 */
public class DeleteEntryFormController extends AbstractController {
    protected BookmarkStore bookmarkStore;
    protected BookmarkSetRequestResolver bookmarkSetRequestResolver;
    
    /**
     * @return Returns the bookmarkSetRequestResolver.
     */
    public BookmarkSetRequestResolver getBookmarkSetRequestResolver() {
        return this.bookmarkSetRequestResolver;
    }

    /**
     * @param bookmarkSetRequestResolver The bookmarkSetRequestResolver to set.
     */
    public void setBookmarkSetRequestResolver(BookmarkSetRequestResolver bookmarkSetRequestResolver) {
        this.bookmarkSetRequestResolver = bookmarkSetRequestResolver;
    }

    /**
     * @return Returns the bookmarkStore.
     */
    public BookmarkStore getBookmarkStore() {
        return this.bookmarkStore;
    }

    /**
     * @param bookmarkStore The bookmarkStore to set.
     */
    public void setBookmarkStore(BookmarkStore bookmarkStore) {
        this.bookmarkStore = bookmarkStore;
    }
    
    
    /**
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @Override
    protected void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
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
            this.logger.warn("No IdPathInfo found for BaseFolder='" + bs + "' and idPath='" + entryIndex + "'");
        }
        
        //Go back to view bookmarks
        response.setRenderParameter("action", "viewBookmarks");
    }
}
