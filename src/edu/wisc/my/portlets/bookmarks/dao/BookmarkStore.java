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

package edu.wisc.my.portlets.bookmarks.dao;

import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

/**
 * The BookmarkStore provides the APIs to use for storing, retrieving and removing BookmarkSets 
 * from a persitent store.
 * 
 * TODO figure out a good package name
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
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
     * TODO should this return something?
     */
    public void removeBookmarkSet(String owner, String name);
    
    
    public Bookmark createBookmark();
}
