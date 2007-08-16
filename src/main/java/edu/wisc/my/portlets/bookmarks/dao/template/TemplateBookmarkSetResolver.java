package edu.wisc.my.portlets.bookmarks.dao.template;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

public interface TemplateBookmarkSetResolver {

	BookmarkSet getTemplateBookmarkSet(String owner, String name, BookmarkStore store);

}
