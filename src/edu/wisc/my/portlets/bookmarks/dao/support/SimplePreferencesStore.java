/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.support;

import java.util.Date;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * Is configured with a single Preferences object which is always returned. It can be modified by
 * the create/store/remove methods.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class SimplePreferencesStore implements PreferencesStore {
    private Preferences preferences;
    
    /**
     * @return Returns the preferences.
     */
    public Preferences getPreferences() {
        return this.preferences;
    }

    /**
     * @param preferences The preferences to set.
     */
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
    
    

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#getPreferences(java.lang.String, java.lang.String)
     */
    public Preferences getPreferences(String owner, String name) {
        this.updatePreferences(owner, name);
        return this.preferences;
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#storePreferences(edu.wisc.my.portlets.bookmarks.domain.Preferences)
     */
    public void storePreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#removePreferences(java.lang.String, java.lang.String)
     */
    public void removePreferences(String owner, String name) {
        this.updatePreferences(owner, name);
        this.preferences = null;
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#createPreferences(java.lang.String, java.lang.String)
     */
    public Preferences createPreferences(String owner, String name) {
        final Preferences p = new Preferences();
        p.setOwner(owner);
        p.setName(name);
        p.setCreated(new Date());
        p.setModified(p.getCreated());
        
        this.setPreferences(p);
        return this.getPreferences(owner, name);
    }

    protected void updatePreferences(String owner, String name) {
        if (this.preferences != null) {
            this.preferences.setOwner(owner);
            this.preferences.setName(name);
        }
    }
}
