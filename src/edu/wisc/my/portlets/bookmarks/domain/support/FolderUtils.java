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
import java.util.List;

import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;

/**
 * TODO write unit tests
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public final class FolderUtils {
    private FolderUtils() { }
    
    public static IndexPathInfo getEntryInfo(Folder baseFolder, String indexPath) {
        return getEntryInfo(baseFolder, indexPath, "\\.");
    }
    //TODO test edge cases
    public static IndexPathInfo getEntryInfo(Folder baseFolder, String indexPath, String regexDelimeter) {
        if (baseFolder == null) {
            throw new IllegalArgumentException("The base Folder can not be null.");
        }
        if (indexPath == null) {
            throw new IllegalArgumentException("The index path can not be null.");
        }
        if (regexDelimeter == null) {
            throw new IllegalArgumentException("The regexDelimeter can not be null.");
        }
        
        final String[] stringIndexes = indexPath.split(regexDelimeter);
        final int[] indexes = new int[stringIndexes.length];
        
        indexes[0] = Integer.parseInt(stringIndexes[0]);

        Folder parent = null;
        Entry target = baseFolder;

        for (int idIndex = 1; idIndex < stringIndexes.length; idIndex++) {
            final int listIndex = Integer.parseInt(stringIndexes[idIndex]);
            
            if (target instanceof Folder) {
                parent = (Folder)target;
                final List<Entry> children = parent.getChildren();
                target = children.get(listIndex);
                indexes[idIndex] = listIndex;
            }
            else {
                throw new IllegalArgumentException("The Entry denoted by the path of ids='" + Arrays.asList(indexes) + "' is not a Folder");
            }
        }

        final IndexPathInfo pathInfo = new IndexPathInfo(indexes, parent, target);
        return pathInfo;
    }
    
    public static boolean deepContains(Folder parent, Entry query) {
        final List<Entry> children = parent.getChildren();
        for (Entry entry : children) {
            if (entry.equals(query)) {
                return true;
            }
            else if (entry instanceof Folder) {
                return deepContains((Folder)entry, query);
            }
        }
        
        return false;
    }
}
