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

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.CollectionFolder;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;
import edu.wisc.my.portlets.bookmarks.web.support.BookmarkSetRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.PreferencesRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.ViewConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import javax.portlet.RenderRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReferenceData {

    private static final Logger logger = LoggerFactory.getLogger(ReferenceData.class);

    @Autowired
    private BookmarkSetRequestResolver bookmarkSetRequestResolver;
    @Autowired
    private PreferencesRequestResolver preferencesRequestResolver;
    @Resource(name="bookmarkStore")
    private BookmarkStore bookmarkStore;
    @Resource(name="availableCollections")
    private Map availableCollections;

    public Map<String, Object> getRefData(RenderRequest request, Errors errors) {
        logger.debug("Entering getRefData()");
        final BookmarkSet bookmarkSet = this.bookmarkSetRequestResolver.getBookmarkSet(request, false);

        final Map<String, Object> refData = new HashMap<>();
        refData.put(ViewConstants.BOOKMARK_SET, bookmarkSet);
        if (errors != null) {
            refData.put(ViewConstants.ERRORS, errors);
        }

        refData.put(ViewConstants.COMMAND_EMPTY_BOOKMARK, new Bookmark());
        refData.put(ViewConstants.COMMAND_EMPTY_FOLDER, new Folder());
        refData.put(ViewConstants.COMMAND_AVAILABLE_COLLECTIONS, this.availableCollections);
        refData.put(ViewConstants.COMMAND_EMPTY_COLLECTION, new CollectionFolder());

        if (request.getRemoteUser() == null) {
            logger.debug("guestMode = true");
            refData.put("guestMode", true);
            refData.put(ViewConstants.OPTIONS, new Preferences());
        } else {
            refData.put("guestMode", false);
            logger.debug("guestMode = false");
            final Preferences preferences = this.preferencesRequestResolver.getPreferences(request, true);
            refData.put(ViewConstants.OPTIONS, preferences);
        }
        return refData;
    }
}
