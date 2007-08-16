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
