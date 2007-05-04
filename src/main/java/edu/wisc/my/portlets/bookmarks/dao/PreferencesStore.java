/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao;

import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * The PreferencesStore provides the APIs to use for storing, retrieving and removing Preferences 
 * from a persitent store.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12161 $
 */
public interface PreferencesStore {
    /**
     * Gets the Preferences for the specified owner and name. If no Preferences can be found for the 
     * owner and name null is retuned.
     * 
     * @param owner The owner of the Preferences to retreive.
     * @param name The name of the Preferences to retrieve.
     * @return The Preferences for the name and owner, null if one is not found for the owner and name.
     */
    public Preferences getPreferences(String owner, String name);
    
    /**
     * Stores new Preferences or updates existing Preferences. Preferences are keyed uniquely
     * using the name and owner fields.
     * 
     * @param preferences The Preferences to persist.
     */
    public void storePreferences(Preferences preferences);
    
    /**
     * Removes Preferences from the persistent store. If a Preferences matching
     * the owner and name cannot be found this is a noop.
     * 
     * @param owner The owner of the Preferences to remove.
     * @param name The name of the Preferences to remove.
     */
    public void removePreferences(String owner, String name);
    
    /**
     * Creates Preferences and stores it in the persistent store.
     * 
     * @param owner The owner of the Preferences to create.
     * @param name The name of the Preferences to create.
     * @return The new Preferences.
     */
    public Preferences createPreferences(String owner, String name);
}
