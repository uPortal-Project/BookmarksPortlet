/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.support;

import java.util.List;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * Provides a way to attempt to retrieve the Preferences from multiple implementations. When retrieving the
 * Preferences the List of PreferencesStores is iterated over. The first Preferences object found is returned.
 * When creating/storing/removing preferences the first PreferencesStore in the List is used. This behavior can
 * be overridden by setting a targetWriteStore which will always be used for the three updating operations. 
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class IteratingPreferencesStore implements PreferencesStore {
    private List<PreferencesStore> delegateStores;
    private PreferencesStore targetWriteStore;
    
    /**
     * @return Returns the delegateStores.
     */
    public List<PreferencesStore> getDelegateStores() {
        return this.delegateStores;
    }

    /**
     * @param delegateStores The delegateStores to set.
     */
    public void setDelegateStores(List<PreferencesStore> delegateStores) {
        this.delegateStores = delegateStores;
    }

    /**
     * @return Returns the targetWriteStore.
     */
    public PreferencesStore getTargetWriteStore() {
        return this.targetWriteStore;
    }

    /**
     * @param targetWriteStore The targetWriteStore to set.
     */
    public void setTargetWriteStore(PreferencesStore targetWriteStore) {
        this.targetWriteStore = targetWriteStore;
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#getPreferences(java.lang.String, java.lang.String)
     */
    public Preferences getPreferences(String owner, String name) {
        for (PreferencesStore store: this.delegateStores) {
            final Preferences prefs = store.getPreferences(owner, name);

            if (prefs != null) {
                return prefs;
            }
        }

        return null;
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#storePreferences(edu.wisc.my.portlets.bookmarks.domain.Preferences)
     */
    public void storePreferences(Preferences preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("Preferences may not be null");
        }

        final PreferencesStore store = this.getWriteStore(preferences.getName(), preferences.getOwner());
        store.storePreferences(preferences);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#removePreferences(java.lang.String, java.lang.String)
     */
    public void removePreferences(String owner, String name) {
        final PreferencesStore store = this.getWriteStore(owner, name);
        store.removePreferences(owner, name);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.PreferencesStore#createPreferences(java.lang.String, java.lang.String)
     */
    public Preferences createPreferences(String owner, String name) {
        final PreferencesStore store = this.getWriteStore(owner, name);
        return store.createPreferences(owner, name);
    }

    /**
     * Gets either the targetWriterStore or if that is null the first PreferencesStore in the delegateStores List. 
     * 
     * @param owner The owner to get the store for
     * @param name The name to get the store for
     * @return The writer PreferencesStore, will never be null.
     * @throws IllegalStateException If targetWriterStore is null and delegateStores is null or of length 0
     */
    protected PreferencesStore getWriteStore(String owner, String name) {
        if (this.targetWriteStore == null && (this.delegateStores == null || this.delegateStores.size() == 0)) {
            throw new IllegalStateException("There are no PreferencesStores configured in the delegateStores list and no targetWriteStore is configured.");
        }
        else if (this.targetWriteStore != null) {
            return this.targetWriteStore;
        }
        else {
            return this.delegateStores.get(0);
        }
    }
}