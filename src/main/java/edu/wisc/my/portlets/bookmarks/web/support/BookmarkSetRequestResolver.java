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

import javax.portlet.PortletRequest;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12161 $
 */
public class BookmarkSetRequestResolver {
    protected BookmarkStore bookmarkStore;
    protected OwnerResolver ownerResolver;
    protected NameResolver nameResolver;
    
    
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
     * @return Returns the nameResolver.
     */
    public NameResolver getNameResolver() {
        return this.nameResolver;
    }

    /**
     * @param nameResolver The nameResolver to set.
     */
    public void setNameResolver(NameResolver nameResolver) {
        this.nameResolver = nameResolver;
    }

    /**
     * @return Returns the ownerResolver.
     */
    public OwnerResolver getOwnerResolver() {
        return this.ownerResolver;
    }

    /**
     * @param ownerResolver The ownerResolver to set.
     */
    public void setOwnerResolver(OwnerResolver ownerResolver) {
        this.ownerResolver = ownerResolver;
    }
    
    

    /**
     * Calls getBookmarkSet(request, true);
     * 
     * @see #getBookmarkSet(PortletRequest, boolean)
     */
    public BookmarkSet getBookmarkSet(PortletRequest request) {
        return this.getBookmarkSet(request, true);
    }
    
    /**
     * Gets a BookmarkSet for the request using the injected {@link OwnerResolver}
     * and {@link NameResolver}.
     * <br>
     * <br>
     * If <code>create</code> is false and no BookmarkSet exists for the name and owner null is returned. 
     * <br>
     * <br>
     * If <code>create</code> is true and no BookmarkSet exists for the name and owner a new BookmarkSet is created. 
     * 
     * @param request The request to resolve the name and owner from.
     * @param create If a BookmarkSet should be created for the name and owner if one does not exist
     * @return The BookmarkSet for the name and owner from the request, null if it does not exists and create is false. If create is true this will never return null.
     */
    public BookmarkSet getBookmarkSet(PortletRequest request, boolean create) {
        final String owner = this.ownerResolver.getOwner(request);
        final String name = this.nameResolver.getBookmarkSetName(request);
        BookmarkSet bookmarkSet = this.bookmarkStore.getBookmarkSet(owner, name);
        
        if (bookmarkSet == null && create) {
            bookmarkSet = this.bookmarkStore.createBookmarkSet(owner, name);
            
            if (bookmarkSet == null) {
                throw new IllegalStateException("Required BookmarkSet is null even after createBookmarkSet was called.");
            }
        }

        return bookmarkSet;
    }
}
