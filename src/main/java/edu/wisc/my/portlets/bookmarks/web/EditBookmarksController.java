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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.CollectionFolder;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;
import edu.wisc.my.portlets.bookmarks.web.support.BookmarkSetRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.PreferencesRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.ViewConstants;

/**
 * Controller resolves the BookmarkSet owner and name for the request and displays it
 * as the model via Edit mode.
 *
 * @author Unknown
 * @version $Id: $Id
 */
public class EditBookmarksController extends AbstractController {
    private BookmarkSetRequestResolver bookmarkSetRequestResolver;
    private PreferencesRequestResolver preferencesRequestResolver;

    /**
     * <p>Getter for the field <code>bookmarkSetRequestResolver</code>.</p>
     *
     * @return Returns the bookmarkSetRequestResolver.
     */
    public BookmarkSetRequestResolver getBookmarkSetRequestResolver() {
        return this.bookmarkSetRequestResolver;
    }

    /**
     * <p>Setter for the field <code>bookmarkSetRequestResolver</code>.</p>
     *
     * @param bookmarkSetRequestResolver The bookmarkSetRequestResolver to set.
     */
    public void setBookmarkSetRequestResolver(BookmarkSetRequestResolver bookmarkSetRequestResolver) {
        this.bookmarkSetRequestResolver = bookmarkSetRequestResolver;
    }

    /**
     * <p>Getter for the field <code>preferencesRequestResolver</code>.</p>
     *
     * @return Returns the preferencesRequestResolver.
     */
    public PreferencesRequestResolver getPreferencesRequestResolver() {
        return this.preferencesRequestResolver;
    }

    /**
     * <p>Setter for the field <code>preferencesRequestResolver</code>.</p>
     *
     * @param preferencesRequestResolver The preferencesRequestResolver to set.
     */
    public void setPreferencesRequestResolver(PreferencesRequestResolver preferencesRequestResolver) {
        this.preferencesRequestResolver = preferencesRequestResolver;
    }


    /** {@inheritDoc} */
    @Override
    protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final BookmarkSet bookmarkSet = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        final Preferences preferences = this.preferencesRequestResolver.getPreferences(request, false);

        final Map<String, Object> refData = new HashMap<String, Object>();
        refData.put(ViewConstants.BOOKMARK_SET, bookmarkSet);
        
        if (preferences != null) {
            refData.put(ViewConstants.OPTIONS, preferences);
        }
        else {
            refData.put(ViewConstants.OPTIONS, new Preferences());
        }

        refData.put(ViewConstants.COMMAND_EMPTY_BOOKMARK, new Bookmark());
        refData.put(ViewConstants.COMMAND_EMPTY_FOLDER, new Folder());
        refData.put(ViewConstants.COMMAND_AVAILABLE_COLLECTIONS, this.availableCollections);
        refData.put(ViewConstants.COMMAND_EMPTY_COLLECTION, new CollectionFolder());
        
        if (request.getRemoteUser() == null) {
            refData.put("guestMode", true);
        } else {
            refData.put("guestMode", false);
        }

        return new ModelAndView("editBookmarks", refData);
    }

    /** {@inheritDoc} */
    @Override
    protected void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        //Allow noop action requests in case people want to use direct links to the portlet
    }

    private Map availableCollections;
    
    /**
     * <p>Setter for the field <code>availableCollections</code>.</p>
     *
     * @param collections a {@link java.util.Map} object.
     */
    public void setAvailableCollections(Map collections) {
    	this.availableCollections = collections;
    }
}
