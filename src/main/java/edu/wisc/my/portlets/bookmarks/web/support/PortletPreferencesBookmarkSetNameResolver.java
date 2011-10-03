/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.wisc.my.portlets.bookmarks.web.support;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * Uses the configured bookmarkSetName as a key into the PortletPreferences to resolve the
 * name of the BookmarkSet for the request.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12161 $
 */
public class PortletPreferencesBookmarkSetNameResolver implements NameResolver {
    public static final String DEFAULT_BOOKMARK_SET_NAME = "bookmarkSetName";
    
    private String bookmarkSetName = DEFAULT_BOOKMARK_SET_NAME;
    

    /**
     * @return Returns the bookmarkSetName.
     */
    public String getBookmarkSetName() {
        return this.bookmarkSetName;
    }

    /**
     * @param bookmarkSetName The bookmarkSetName to set.
     */
    public void setBookmarkSetName(String bookmarkSetName) {
        this.bookmarkSetName = bookmarkSetName;
    }


    /**
     * @see edu.wisc.my.portlets.bookmarks.web.support.NameResolver#getBookmarkSetName(javax.portlet.PortletRequest)
     */
    public String getBookmarkSetName(PortletRequest request) {
        final PortletPreferences prefs = request.getPreferences();
        return prefs.getValue(this.bookmarkSetName, null);
    }
}
