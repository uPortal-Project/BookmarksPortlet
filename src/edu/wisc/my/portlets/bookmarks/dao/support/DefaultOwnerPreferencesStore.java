/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.support;

import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * Overrides the owner in the APIs with the defaultOwner. Has the affect that one Preferences object
 * is shared for all owners using a specific name.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class DefaultOwnerPreferencesStore extends PreferencesStoreWrapper {
    private String defaultOwner = null;
    
    /**
     * @return Returns the defaultOwner.
     */
    public String getDefaultOwner() {
        return this.defaultOwner;
    }

    /**
     * @param defaultOwner The defaultOwner to set.
     */
    public void setDefaultOwner(String defaultOwner) {
        this.defaultOwner = defaultOwner;
    }


    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.support.PreferencesStoreWrapper#createPreferences(java.lang.String, java.lang.String)
     */
    @Override
    public Preferences createPreferences(String owner, String name) {
        return super.createPreferences(this.defaultOwner, name);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.support.PreferencesStoreWrapper#getPreferences(java.lang.String, java.lang.String)
     */
    @Override
    public Preferences getPreferences(String owner, String name) {
        return super.getPreferences(this.defaultOwner, name);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.support.PreferencesStoreWrapper#removePreferences(java.lang.String, java.lang.String)
     */
    @Override
    public void removePreferences(String owner, String name) {
        super.removePreferences(this.defaultOwner, name);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.support.PreferencesStoreWrapper#storePreferences(edu.wisc.my.portlets.bookmarks.domain.Preferences)
     */
    @Override
    public void storePreferences(Preferences preferences) {
        final String realOwner = preferences.getOwner();
        preferences.setOwner(this.defaultOwner);
        super.storePreferences(preferences);
        preferences.setOwner(realOwner);
    }
}
