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
package edu.wisc.my.portlets.bookmarks.dao.template;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

/**
 * <p>TemplateBookmarkSetResolver interface.</p>
 *
 * @author Unknown
 * @version $Id: $Id
 */
public interface TemplateBookmarkSetResolver {

	/**
	 * <p>getTemplateBookmarkSet.</p>
	 *
	 * @param owner a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param store a {@link edu.wisc.my.portlets.bookmarks.dao.BookmarkStore} object.
	 * @return a {@link edu.wisc.my.portlets.bookmarks.domain.BookmarkSet} object.
	 */
	BookmarkSet getTemplateBookmarkSet(String owner, String name, BookmarkStore store);

}
