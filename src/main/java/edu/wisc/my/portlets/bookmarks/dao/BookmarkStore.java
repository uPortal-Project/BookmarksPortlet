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
package edu.wisc.my.portlets.bookmarks.dao;

import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

/**
 * The BookmarkStore provides the APIs to use for storing, retrieving and removing BookmarkSets
 * from a persitent store.
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12161 $
 */
public interface BookmarkStore {
    /**
     * Gets a BookmarkSet for the specified owner and name. If no BookmarkSet can be found for the
     * owner and name null is retuned.
     *
     * @param owner The owner of the BookmarkSet to retreive.
     * @param name The name of the BookmarkSet to retrieve.
     * @return The BookmarkSet for the name and owner, null if one is not found for the owner and name.
     */
    public BookmarkSet getBookmarkSet(String owner, String name);
    
    /**
     * Stores a new BookmarkSet or updates an existing BookmarkSet. BookmarkSets are keyed uniquely
     * using the name and owner fields.
     *
     * @param bookmarkSet The BookmarkSet to persist.
     * TODO should this return something?
     */
    public void storeBookmarkSet(BookmarkSet bookmarkSet);
    
    /**
     * Removes a BookmarkSet from the persistent store. If a BookmarkSet matching
     * the owner and name cannot be found this is a noop.
     *
     * @param owner The owner of the BookmarkSet to remove.
     * @param name The name of the BookmarkSet to remove.
     */
    public void removeBookmarkSet(String owner, String name);
    
    /**
     * Creates a BookmarkSet and stores it in the persistent store.
     *
     * @param owner The owner of the BookmarkSet to create.
     * @param name The name of the BookmarkSet to create.
     * @return The new BookmarkSet.
     */
    public BookmarkSet createBookmarkSet(String owner, String name);
}
