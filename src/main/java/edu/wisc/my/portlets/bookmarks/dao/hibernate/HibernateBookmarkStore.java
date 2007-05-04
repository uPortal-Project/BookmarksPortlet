/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.hibernate;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
 * @version $Revision: 12173 $
 */
public class HibernateBookmarkStore extends HibernateDaoSupport implements BookmarkStore {
    private static final String PARAM_OWNER = "owner";
    private static final String PARAM_NAME = "name";

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
                //Ensure there won't be duplicate entries with this owner & name
                this.removeBookmarkSet(bookmarkSet.getOwner(), bookmarkSet.getName());
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
            
            final BookmarkSet set = this.getBookmarkSet(owner, name);
            if (set != null) {
                session.delete(set);
            }
            
            session.flush();
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }
    

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#createBookmarkSet(java.lang.String, java.lang.String)
     */
    public BookmarkSet createBookmarkSet(String owner, String name) {
        final BookmarkSet bookmarkSet = new BookmarkSet();
        bookmarkSet.setOwner(owner);
        bookmarkSet.setName(name);
        bookmarkSet.setCreated(new Date());
        bookmarkSet.setModified(bookmarkSet.getCreated());

        this.storeBookmarkSet(bookmarkSet);
        
        return bookmarkSet;
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
