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
package edu.wisc.my.portlets.bookmarks.dao.template;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

public class SimpleTemplateBookmarkSetResolver implements TemplateBookmarkSetResolver {

	// Instance Members.
	private final String templateOwner;
	private final String templateName;

	/*
	 * Public API.
	 */

	public SimpleTemplateBookmarkSetResolver(String templateOwner) {
		this(templateOwner, null);
	}

	public SimpleTemplateBookmarkSetResolver(String templateOwner, String templateName) {

		// Assertions.
		if (templateOwner == null) {
			String msg = "Argument 'templateOwner' cannot be null.";
			throw new IllegalArgumentException(msg);
		}
		// NB:  Argument 'templateName' may be null.

		// Instance Members.
		this.templateOwner = templateOwner;
		this.templateName = templateName;

	}

	public BookmarkSet getTemplateBookmarkSet(String owner, String name, BookmarkStore store) {

		// Assertions.
		if (owner == null) {
			String msg = "Argument 'owner' cannot be null.";
			throw new IllegalArgumentException(msg);
		}
		// NB:  Argument 'name' may be null.
		if (store == null) {
			String msg = "Argument 'store' cannot be null.";
			throw new IllegalArgumentException(msg);
		}

		BookmarkSet rslt = store.getBookmarkSet(templateOwner, templateName);

		return rslt;

	}

}
