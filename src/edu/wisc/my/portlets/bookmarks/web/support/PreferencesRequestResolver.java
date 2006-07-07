/*******************************************************************************
 * Copyright 2006, The Board of Regents of the University of Wisconsin System.
 * All rights reserved.
 *
 * A non-exclusive worldwide royalty-free license is granted for this Software.
 * Permission to use, copy, modify, and distribute this Software and its
 * documentation, with or without modification, for any purpose is granted
 * provided that such redistribution and use in source and binary forms, with or
 * without modification meets the following conditions:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *c
 * 3. Redistributions of any form whatsoever must retain the following
 * acknowledgement:
 *
 * "This product includes software developed by The Board of Regents of
 * the University of Wisconsin System."
 *
 *THIS SOFTWARE IS PROVIDED BY THE BOARD OF REGENTS OF THE UNIVERSITY OF
 *WISCONSIN SYSTEM "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING,
 *BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE BOARD OF REGENTS OF
 *THE UNIVERSITY OF WISCONSIN SYSTEM BE LIABLE FOR ANY DIRECT, INDIRECT,
 *INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 *OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/


package edu.wisc.my.portlets.bookmarks.web.support;

import javax.portlet.PortletRequest;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
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
