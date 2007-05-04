/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
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
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12164 $
 */
public class PreferencesFormController extends BaseBookmarksFormController {
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
     * @see org.springframework.web.portlet.mvc.AbstractFormController#formBackingObject(javax.portlet.PortletRequest)
     */
    @Override
    protected Object formBackingObject(PortletRequest request) throws Exception {
        Preferences prefs = this.preferencesRequestResolver.getPreferences(request);
        return prefs;
    }

    /**
     * @see org.springframework.web.portlet.mvc.SimpleFormController#onSubmitAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
        final Preferences prefs = (Preferences)command;
        prefs.setModified(new Date());
        this.preferencesStore.storePreferences(prefs);
    }
}
