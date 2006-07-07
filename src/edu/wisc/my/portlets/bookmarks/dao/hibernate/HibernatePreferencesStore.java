/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.hibernate;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * Persists and retrieves Preferences objects via Hibernate.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class HibernatePreferencesStore extends HibernateDaoSupport implements PreferencesStore {
    private static final String PARAM_OWNER = "owner";
    private static final String PARAM_NAME = "name";
    private static final String DELETE_PREFERENCES__BOTH_NULL  = "DELETE_PREFERENCES__BOTH_NULL";
    private static final String DELETE_PREFERENCES__NAME_NULL  = "DELETE_PREFERENCES__NAME_NULL";
    private static final String DELETE_PREFERENCES__OWNER_NULL = "DELETE_PREFERENCES__OWNER_NULL";
    private static final String DELETE_PREFERENCES__NO_NULL    = "DELETE_PREFERENCES__NO_NULL";

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#getPreferences(java.lang.String, java.lang.String)
     */
    public Preferences getPreferences(String owner, String name) {
        try {
            final Session session = this.getSession(false);
            
            final Criteria c = session.createCriteria(Preferences.class);
            c.add(Restrictions.and(this.smartEqual(PARAM_NAME, name),
                                   this.smartEqual(PARAM_OWNER, owner)));
            c.setCacheable(true);
            
            final Preferences bs = (Preferences)c.uniqueResult();
            
            return bs;
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#storePreferences(edu.wisc.my.portlets.bookmarks.domain.Preferences)
     */
    public void storePreferences(Preferences bookmarkSet) {
        try {
            final Session session = this.getSession(false);
            
            //If the Preferences is new it must be saved first
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
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#removePreferences(java.lang.String, java.lang.String)
     */
    public void removePreferences(String owner, String name) {
        try {
            final Session session = this.getSession(false);
            final Query q;

            if (owner != null && name != null) {
                q = session.getNamedQuery(DELETE_PREFERENCES__NO_NULL);
                q.setParameter(PARAM_NAME, name);
                q.setParameter(PARAM_OWNER, owner);
            }
            else if (name != null) {
                q = session.getNamedQuery(DELETE_PREFERENCES__OWNER_NULL);
                q.setParameter(PARAM_NAME, name);
            }
            else if (owner != null) {
                q = session.getNamedQuery(DELETE_PREFERENCES__NAME_NULL);
                q.setParameter(PARAM_OWNER, name);
            }
            else {
                q = session.getNamedQuery(DELETE_PREFERENCES__BOTH_NULL);
            }

            q.executeUpdate();
            session.flush();
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
    }
    

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#createPreferences(java.lang.String, java.lang.String)
     */
    public Preferences createPreferences(String owner, String name) {
        final Preferences bookmarkSet = new Preferences();
        bookmarkSet.setOwner(owner);
        bookmarkSet.setName(name);
        bookmarkSet.setCreated(new Date());
        bookmarkSet.setModified(bookmarkSet.getCreated());

        this.storePreferences(bookmarkSet);
        
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
