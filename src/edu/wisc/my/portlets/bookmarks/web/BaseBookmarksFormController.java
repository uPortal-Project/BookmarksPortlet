/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.web;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.portlet.mvc.SimpleFormController;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;
import edu.wisc.my.portlets.bookmarks.web.support.ViewConstants;
import edu.wisc.my.portlets.bookmarks.web.support.BookmarkSetRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.PreferencesRequestResolver;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class BaseBookmarksFormController extends SimpleFormController {
    protected BookmarkSetRequestResolver bookmarkSetRequestResolver;
    protected PreferencesRequestResolver preferencesRequestResolver;
    protected BookmarkStore bookmarkStore;
    

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
     * @return Returns the preferencesRequestResolver.
     */
    public PreferencesRequestResolver getPreferencesRequestResolver() {
        return this.preferencesRequestResolver;
    }

    /**
     * @param preferencesRequestResolver The preferencesRequestResolver to set.
     */
    public void setPreferencesRequestResolver(PreferencesRequestResolver preferencesRequestResolver) {
        this.preferencesRequestResolver = preferencesRequestResolver;
    }


    /**
     * @see org.springframework.web.portlet.mvc.SimpleFormController#referenceData(javax.portlet.PortletRequest, java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    protected Map referenceData(PortletRequest request, Object command, Errors errors) throws Exception {
        final BookmarkSet bookmarkSet = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);
        final Preferences preferences = this.preferencesRequestResolver.getPreferences(request, false);

        final Map<String, Object> refData = new HashMap<String, Object>();
        refData.put(ViewConstants.BOOKMARK_SET, bookmarkSet);
        refData.put(ViewConstants.PREFERENCES, preferences);
        refData.put(ViewConstants.ERRORS, errors);

        refData.put(ViewConstants.COMMAND_EMPTY_BOOKMARK, new Bookmark());
        refData.put(ViewConstants.COMMAND_EMPTY_FOLDER, new Folder());
        refData.put(ViewConstants.COMMAND_EMPTY_OPTIONS, new Preferences());

        return refData;
    }
}
