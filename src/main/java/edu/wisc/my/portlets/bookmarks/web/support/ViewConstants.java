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
package edu.wisc.my.portlets.bookmarks.web.support;

/**
 * ViewConstants used for passing data to the rendering via the request
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12164 $
 */
public final class ViewConstants {
    public static final String BOOKMARK_SET = "bookmarkSet";
    public static final String OPTIONS      = "options";
    public static final String ERRORS       = "errors";
    
    public static final String COMMAND_EMPTY_FOLDER     = "emptyFolderCommand";
    public static final String COMMAND_EMPTY_COLLECTION     = "emptyCollectionCommand";
    public static final String COMMAND_EMPTY_BOOKMARK   = "emptyBookmarkCommand";

    public static final String COMMAND_FOLDER   = "folderCommand";
    public static final String COMMAND_COLLECTION   = "collectionCommand";
    public static final String COMMAND_BOOKMARK = "bookmarkCommand";
    
    public static final String COMMAND_AVAILABLE_COLLECTIONS = "availableCollections";
}
