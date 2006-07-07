/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.support;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * Wraps a PreferencesStore and delegates to the wrapped store.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class PreferencesStoreWrapper implements PreferencesStore {
    private PreferencesStore preferencesStore;
    
    /**
     * @return Returns the preferencesStore.
     */
    public PreferencesStore getPreferencesStore() {
        return this.preferencesStore;
    }

    /**
     * @param preferencesStore The preferencesStore to set.
     */
    public void setPreferencesStore(PreferencesStore preferencesStore) {
        this.preferencesStore = preferencesStore;
    }
    
    

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#createPreferences(java.lang.String, java.lang.String)
     */
    public Preferences createPreferences(String owner, String name) {
        this.checkState();
        return this.preferencesStore.createPreferences(owner, name);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#getPreferences(java.lang.String, java.lang.String)
     */
    public Preferences getPreferences(String owner, String name) {
        this.checkState();
        return this.preferencesStore.getPreferences(owner, name);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#removePreferences(java.lang.String, java.lang.String)
     */
    public void removePreferences(String owner, String name) {
        this.checkState();
        this.preferencesStore.removePreferences(owner, name);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#storePreferences(edu.wisc.my.portlets.bookmarks.domain.Preferences)
     */
    public void storePreferences(Preferences preferences) {
        this.checkState();
        this.preferencesStore.storePreferences(preferences);
    }
    
    
    /**
     * Checks that the wrapper is in a valid state
     */
    private void checkState() {
        if (this.preferencesStore == null) {
            throw new IllegalStateException("There is no delegate preferencesStore configured.");
        }
    }
}
