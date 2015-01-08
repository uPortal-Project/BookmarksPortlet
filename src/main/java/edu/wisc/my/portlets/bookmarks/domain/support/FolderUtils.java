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
package edu.wisc.my.portlets.bookmarks.domain.support;

import java.util.HashMap;
import java.util.Map;

import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12175 $
 */
public final class FolderUtils {
    private FolderUtils() { }
    
    public static IdPathInfo getEntryInfo(Folder baseFolder, String indexPath) {
        return getEntryInfo(baseFolder, indexPath, "\\.");
    }
    
    /**
     * @param baseFolder
     * @param idPath
     * @param regexDelimeter
     * @return The IdPathInfo that represents the found Entry, null if no entry is found for the idPath.
     */
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
                return null;
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
