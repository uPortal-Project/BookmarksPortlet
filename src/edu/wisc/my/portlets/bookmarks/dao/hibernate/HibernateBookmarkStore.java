/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class HibernateBookmarkStore extends HibernateDaoSupport implements BookmarkStore {

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#getBookmarkSet(java.lang.String, java.lang.String)
     */
    public BookmarkSet getBookmarkSet(String owner, String name) {
        try {
            final Session session = this.getSession(false);
            
            final Criteria c = session.createCriteria(BookmarkSet.class);
            
            c.add(Restrictions.and(this.smartEqual("name", name),
                                   this.smartEqual("owner", owner)));
            final List sets = c.list();
            
            return (BookmarkSet)DataAccessUtils.uniqueResult(sets);
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#storeBookmarkSet(edu.wisc.my.portlets.bookmarks.domain.BookmarkSet)
     */
    public void storeBookmarkSet(BookmarkSet bookmarkSet) {
        try {
            final Session session = this.getSession(false);
            if (bookmarkSet.getId() == -1) {
                session.save(bookmarkSet);
            }
            session.update(bookmarkSet);
            session.flush();
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#removeBookmarkSet(java.lang.String, java.lang.String)
     */
    public void removeBookmarkSet(String owner, String name) {
        try {
            final Session session = this.getSession(false);
            final BookmarkSet bs = this.getBookmarkSet(owner, name);
            session.delete(bs);
            session.flush();
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }
    
    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#createBookmark()
     */
    public Bookmark createBookmark() {
        try {
            final Session session = this.getSession(false);
            
            final Bookmark b = new Bookmark();
            session.save(b);
            session.flush();

            return b;
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

    private Criterion smartEqual(String property, Object value) {
        return value == null ? Restrictions.isNull(property) : Restrictions.eq(property, value);
    }
}
