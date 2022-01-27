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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.CollectionFolder;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;
import edu.wisc.my.portlets.bookmarks.web.support.BookmarkSetRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.PreferencesRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.ViewConstants;

/**
 * <p>BaseBookmarksFormController class.</p>
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12168 $
 */
public class BaseBookmarksFormController {
    private String handlerMappingParameter = "action";

    protected BookmarkSetRequestResolver bookmarkSetRequestResolver;
    protected PreferencesRequestResolver preferencesRequestResolver;
    protected BookmarkStore bookmarkStore;

    protected Object formBackingObject(PortletRequest request) throws Exception {
        //TODO if move return default object
        //TODO if no move get real object from store for updating
        return null;
    }

    protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {

    }

    /**
     * <p>Getter for the field <code>handlerMappingParameter</code>.</p>
     *
     * @return Returns the handlerMappingParameter.
     */
    public String getHandlerMappingParameter() {
        return this.handlerMappingParameter;
    }

    /**
     * <p>Setter for the field <code>handlerMappingParameter</code>.</p>
     *
     * @param handlerMappingParameter The handlerMappingParameter to set.
     */
    public void setHandlerMappingParameter(String handlerMappingParameter) {
        this.handlerMappingParameter = handlerMappingParameter;
    }

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
     * <p>Getter for the field <code>bookmarkStore</code>.</p>
     *
     * @return Returns the bookmarkStore.
     */
    public BookmarkStore getBookmarkStore() {
        return this.bookmarkStore;
    }

    /**
     * <p>Setter for the field <code>bookmarkStore</code>.</p>
     *
     * @param bookmarkStore The bookmarkStore to set.
     */
    public void setBookmarkStore(BookmarkStore bookmarkStore) {
        this.bookmarkStore = bookmarkStore;
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
    protected Map referenceData(PortletRequest request, Object command, Errors errors) throws Exception {
        final BookmarkSet bookmarkSet = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        final Preferences preferences = this.preferencesRequestResolver.getPreferences(request, false);

        final Map<String, Object> refData = new HashMap<String, Object>();
        refData.put(ViewConstants.BOOKMARK_SET, bookmarkSet);
        refData.put(ViewConstants.ERRORS, errors);

        if (preferences != null) {
            refData.put(ViewConstants.OPTIONS, preferences);
        }
        else {
            refData.put(ViewConstants.OPTIONS, new Preferences());
        }

        refData.put(ViewConstants.COMMAND_EMPTY_BOOKMARK, new Bookmark());
        refData.put(ViewConstants.COMMAND_EMPTY_FOLDER, new Folder());
        refData.put(ViewConstants.COMMAND_EMPTY_COLLECTION, new CollectionFolder());

        return refData;
    }

    /** {@inheritDoc} */
    protected void processFormSubmission(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {

    	// don't save preferences for guest users
    	if (request.getRemoteUser() == null)
    		return;

        //super.processFormSubmission(request, response, command, errors);

        if (errors.getErrorCount() <= 0) {
            response.setRenderParameter(this.handlerMappingParameter, "viewBookmarks");
        }
    }

}
