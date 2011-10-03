/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
