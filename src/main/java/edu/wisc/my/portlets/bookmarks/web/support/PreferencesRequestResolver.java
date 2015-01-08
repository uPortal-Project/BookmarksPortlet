/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.wisc.my.portlets.bookmarks.web.support;

import javax.portlet.PortletRequest;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12161 $
 */
public class PreferencesRequestResolver {
    protected PreferencesStore preferencesStore;
    protected OwnerResolver ownerResolver;
    protected NameResolver nameResolver;
    
    
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
     * @return Returns the nameResolver.
     */
    public NameResolver getNameResolver() {
        return this.nameResolver;
    }

    /**
     * @param nameResolver The nameResolver to set.
     */
    public void setNameResolver(NameResolver nameResolver) {
        this.nameResolver = nameResolver;
    }

    /**
     * @return Returns the ownerResolver.
     */
    public OwnerResolver getOwnerResolver() {
        return this.ownerResolver;
    }

    /**
     * @param ownerResolver The ownerResolver to set.
     */
    public void setOwnerResolver(OwnerResolver ownerResolver) {
        this.ownerResolver = ownerResolver;
    }
    
    

    /**
     * Calls getPreferences(request, true);
     * 
     * @see #getPreferences(PortletRequest, boolean)
     */
    public Preferences getPreferences(PortletRequest request) {
        return this.getPreferences(request, true);
    }
    
    /**
     * Gets Preferences for the request using the injected {@link OwnerResolver}
     * and {@link NameResolver}.
     * <br>
     * <br>
     * If <code>create</code> is false and no Preferences exists for the name and owner null is returned. 
     * <br>
     * <br>
     * If <code>create</code> is true and no Preferences exists for the name and owner a new Preferences is created. 
     * 
     * @param request The request to resolve the name and owner from.
     * @param create If a Preferences should be created for the name and owner if one does not exist
     * @return The Preferences for the name and owner from the request, null if it does not exists and create is false. If create is true this will never return null.
     */
    public Preferences getPreferences(PortletRequest request, boolean create) {
        final String owner = this.ownerResolver.getOwner(request);
        final String name = this.nameResolver.getBookmarkSetName(request);
        Preferences preferences = this.preferencesStore.getPreferences(owner, name);
        
        if (preferences == null && create) {
            preferences = this.preferencesStore.createPreferences(owner, name);
            
            if (preferences == null) {
                throw new IllegalStateException("Required Preferences is null even after createPreferences was called.");
            }
        }

        return preferences;
    }
}
