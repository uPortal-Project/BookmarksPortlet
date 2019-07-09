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
package edu.wisc.my.portlets.bookmarks.web;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.springframework.validation.BindException;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * <p>PreferencesFormController class.</p>
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12164 $
 */
public class PreferencesFormController extends BaseBookmarksFormController {
    private PreferencesStore preferencesStore;
    
    
    /**
     * <p>Getter for the field <code>preferencesStore</code>.</p>
     *
     * @return Returns the preferencesStore.
     */
    public PreferencesStore getPreferencesStore() {
        return this.preferencesStore;
    }
    /**
     * <p>Setter for the field <code>preferencesStore</code>.</p>
     *
     * @param preferencesStore The preferencesStore to set.
     */
    public void setPreferencesStore(PreferencesStore preferencesStore) {
        this.preferencesStore = preferencesStore;
    }
    
    /** {@inheritDoc} */
    @Override
    protected Object formBackingObject(PortletRequest request) throws Exception {
        Preferences prefs = this.preferencesRequestResolver.getPreferences(request);
        return prefs;
    }

    /** {@inheritDoc} */
    @Override
    protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
        final Preferences prefs = (Preferences)command;
        prefs.setModified(new Date());
        this.preferencesStore.storePreferences(prefs);
    }
}
