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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;

public class StaticBookmarkStore implements BookmarkStore {
    private static int id = 0;
    private BookmarkSet bookmarkSet;
    
    public StaticBookmarkStore() {
        this.resetStaticBookmarkStore();
    }
    
    private void resetStaticBookmarkStore() {
        this.bookmarkSet = this.populateBookmarkSet(new BookmarkSet(),
                                                    "Static Bookmarks",
                                                    "edalquist",
                                                    "Notes",
                                                    this.populateFolder(new Folder(),
                                                                        "Folder 1",
                                                                        null,
                                                                        false,
                                                                        this.populateBookmark(new Bookmark(), "Bookmark 1.1", null, "http://www.google.com", true),
                                                                        this.populateBookmark(new Bookmark(), "Bookmark 1.2", "Notes!!!!", "htttps://my.wisc.edu", true)),
                                                    this.populateFolder(new Folder(),
                                                                        "Folder 2",
                                                                        "Folder 2 is 1337",
                                                                        true,
                                                                        this.populateBookmark(new Bookmark(), "Bookmark 2.1", null, "http://www.dalquist.org", false)),
                                                    this.populateBookmark(new Bookmark(), "Bookmark 1", null, "http://www.wisc.edu", false),
                                                    this.populateBookmark(new Bookmark(), "Bookmark 2", null, "http://www.mtu.edu", true));
        
    }
    
    private BookmarkSet populateBookmarkSet(BookmarkSet bs, String name, String owner, String notes, Entry... children) {
        this.populateFolder(bs, name, notes, false, children);
        bs.setOwner(owner);
        return bs;
    }
    
    private Folder populateFolder(Folder f, String name, String notes, boolean minimized, Entry... children) {
        this.populateEntry(name, notes, f);
        f.setMinimized(minimized);
        
        final List<Entry> childList = new ArrayList<Entry>();
        for (Entry entry : children) {
            childList.add(entry);
        }
        
        Collections.sort(childList);
        
        f.setChildren(childList);
        return f;
    }

    private Bookmark populateBookmark(Bookmark b, String name, String notes, String url, boolean newWindow) {
        this.populateEntry(name, notes, b);
        b.setUrl(url);
        b.setNewWindow(newWindow);
        return b;
    }

    private Entry populateEntry(String name, String notes, Entry e) {
        e.setName(name);
        e.setCreated(new Date());
        e.setModified(e.getCreated());
        e.setNote(notes);
        e.setId(id++);
        return e;
    }
    
    public BookmarkSet getBookmarkSet(String owner, String name) {
        return this.bookmarkSet;
    }

    public void storeBookmarkSet(BookmarkSet bookmarkSet) {
        this.bookmarkSet = bookmarkSet;
    }

    public void removeBookmarkSet(String owner, String name) {
        this.resetStaticBookmarkStore();
    }

}
