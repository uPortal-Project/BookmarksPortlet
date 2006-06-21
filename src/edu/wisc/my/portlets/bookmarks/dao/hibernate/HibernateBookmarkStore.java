/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

/**
 * Persists and retrieves BookmarkSet objects via Hibernate.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class HibernateBookmarkStore extends HibernateDaoSupport implements BookmarkStore {
    private static final String PARAM_OWNER = "owner";
    private static final String PARAM_NAME = "name";
    private static final String DELETE_BOOKMARK_SET__BOTH_NULL  = "DELETE_BOOKMARK_SET__BOTH_NULL";
    private static final String DELETE_BOOKMARK_SET__NAME_NULL  = "DELETE_BOOKMARK_SET__NAME_NULL";
    private static final String DELETE_BOOKMARK_SET__OWNER_NULL = "DELETE_BOOKMARK_SET__OWNER_NULL";
    private static final String DELETE_BOOKMARK_SET__NO_NULL    = "DELETE_BOOKMARK_SET__NO_NULL";

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#getBookmarkSet(java.lang.String, java.lang.String)
     */
    public BookmarkSet getBookmarkSet(String owner, String name) {
        try {
            final Session session = this.getSession(false);
            
            final Criteria c = session.createCriteria(BookmarkSet.class);
            c.add(Restrictions.and(this.smartEqual(PARAM_NAME, name),
                                   this.smartEqual(PARAM_OWNER, owner)));
            c.setCacheable(true);
            
            final BookmarkSet bs = (BookmarkSet)c.uniqueResult();
            
            return bs;
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
            
            //If the BookmarkSet is new it must be saved first
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
            final Query q;

            if (owner != null && name != null) {
                q = session.getNamedQuery(DELETE_BOOKMARK_SET__NO_NULL);
                q.setParameter(PARAM_NAME, name);
                q.setParameter(PARAM_OWNER, owner);
            }
            else if (name != null) {
                q = session.getNamedQuery(DELETE_BOOKMARK_SET__OWNER_NULL);
                q.setParameter(PARAM_NAME, name);
            }
            else if (owner != null) {
                q = session.getNamedQuery(DELETE_BOOKMARK_SET__NAME_NULL);
                q.setParameter(PARAM_OWNER, name);
            }
            else {
                q = session.getNamedQuery(DELETE_BOOKMARK_SET__BOTH_NULL);
            }

            q.executeUpdate();
            session.flush();
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

    /**
     * There are two different Criterion for if a property is null or checking equality. This is a
     * convience method to return the one based on if value is null or not.
     */
    private Criterion smartEqual(String property, Object value) {
        if (value == null) {
            return Restrictions.isNull(property);
        }
        else {
            return Restrictions.eq(property, value);
        }
    }
}
