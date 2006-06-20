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

package edu.wisc.my.portlets.bookmarks.domain.support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public final class FolderUtils {
    private FolderUtils() { }
    
    public static IdPathInfo getEntryInfo(Folder baseFolder, String indexPath) {
        return getEntryInfo(baseFolder, indexPath, "\\.");
    }
    
    public static IdPathInfo getEntryInfo(Folder baseFolder, String idPath, String regexDelimeter) {
        if (baseFolder == null) {
            throw new IllegalArgumentException("The base Folder can not be null.");
        }
        if (idPath == null) {
            throw new IllegalArgumentException("The ID path can not be null.");
        }
        if (regexDelimeter == null) {
            throw new IllegalArgumentException("The regexDelimeter can not be null.");
        }
        
        final String[] stringIds = idPath.split(regexDelimeter);
        final long[] ids = new long[stringIds.length];
        
        ids[0] = Long.parseLong(stringIds[0]);

        Folder parent = null;
        Entry target = baseFolder;

        for (int idIndex = 1; idIndex < stringIds.length; idIndex++) {
            final long entryId = Long.parseLong(stringIds[idIndex]);
            
            if (target instanceof Folder) {
                parent = (Folder)target;
                final Map<Long, Entry> children = parent.getChildren();
                target = children.get(entryId);
                ids[idIndex] = entryId;
            }
            else {
                throw new IllegalArgumentException("The Entry denoted by the path of ids='" + Arrays.asList(ids) + "' is not a Folder");
            }
        }

        final IdPathInfo pathInfo = new IdPathInfo(ids, parent, target);
        return pathInfo;
    }
    
    
    
    public static boolean deepContains(Folder parent, Entry query) {
        final Map<Long, Entry> children = parent.getChildren();
       
        for (Map.Entry<Long, Entry> mapEntry : children.entrySet()) {
            final Entry child = mapEntry.getValue();
            
            if (child.equals(query)) {
                return true;
            }
            else if (child instanceof Folder) {
                return deepContains((Folder)child, query);
            }
        }
        
        return false;
    }
    
    
    public static Entry deepClone(Entry target, boolean copyIds) {
        if (target instanceof BookmarkSet) {
            return deepCloneBookmarkSet((BookmarkSet)target, copyIds);
        }
        else if (target instanceof Folder) {
            return deepCloneFolder((Folder)target, copyIds);
        }
        else if (target instanceof Bookmark) {
            return deepCloneBookmark((Bookmark)target, copyIds);
        }
        else {
            return deepCloneEntry(target, copyIds);
        }
    }
    
    public static Entry deepCloneEntry(Entry target, boolean copyIds) {
        if (target == null) {
            return null;
        }
        
        final Entry clone = new Entry();
        copyEntryFields(target, clone, copyIds);
        
        return clone;
    }
    
    public static Bookmark deepCloneBookmark(Bookmark target, boolean copyIds) {
        if (target == null) {
            return null;
        }
        
        final Bookmark clone = new Bookmark();
        copyEntryFields(target, clone, copyIds);
        copyBookmarkFields(target, clone);
        return clone;
    }
    
    public static Folder deepCloneFolder(Folder target, boolean copyIds) {
        if (target == null) {
            return null;
        }
        
        final Folder clone = new Folder();
        copyEntryFields(target, clone, copyIds);
        copyFolderFields(target, clone, copyIds);
        return clone;
    }
    
    public static BookmarkSet deepCloneBookmarkSet(BookmarkSet target, boolean copyIds) {
        if (target == null) {
            return null;
        }
        
        final BookmarkSet clone = new BookmarkSet();
        copyEntryFields(target, clone, copyIds);
        copyFolderFields(target, clone, copyIds);
        copyBookmarkSetFields(target, clone);
        return clone;
    }

    private static void copyBookmarkSetFields(BookmarkSet target, final BookmarkSet clone) {
        target.setName(clone.getName());
    }

    private static void copyBookmarkFields(Bookmark target, Bookmark clone) {
        clone.setUrl(target.getUrl());
        clone.setNewWindow(target.isNewWindow());
    }


    private static void copyFolderFields(Folder target, Folder clone, boolean copyIds) {
        clone.setMinimized(target.isMinimized());
        clone.setChildComparator(target.getChildComparator());

        final Map<Long, Entry> clonedChildren = new HashMap<Long, Entry>();
        final Map<Long, Entry> children = target.getChildren();

        for (Entry targetEntry : children.values()) {
            final Entry clonedEntry = deepClone(targetEntry, copyIds);
            
            if (copyIds) {
                clonedChildren.put(clonedEntry.getId(), clonedEntry);
            }
            else {
                clonedChildren.put((long)clonedEntry.hashCode(), clonedEntry);
            }
        }
        
        clone.setChildren(clonedChildren);
    }
    
    private static void copyEntryFields(Entry target, Entry clone, boolean copyIds) {
        if (copyIds) {
            clone.setId(target.getId());
        }

        clone.setName(target.getName());
        clone.setNote(target.getNote());
        clone.setCreated(target.getCreated());
        clone.setModified(target.getModified());
    }
}
