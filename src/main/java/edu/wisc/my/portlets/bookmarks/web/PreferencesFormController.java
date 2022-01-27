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

import javax.annotation.Resource;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.validation.Valid;

import edu.wisc.my.portlets.bookmarks.domain.validation.PreferencesValidator;
import edu.wisc.my.portlets.bookmarks.web.support.PreferencesRequestResolver;
import edu.wisc.my.portlets.bookmarks.web.support.ViewConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import edu.wisc.my.portlets.bookmarks.dao.PreferencesStore;
import edu.wisc.my.portlets.bookmarks.domain.Preferences;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * PreferencesFormController class.
 */
@Controller
@RequestMapping("VIEW")
public class PreferencesFormController {

    private static final Logger logger = LoggerFactory.getLogger(PreferencesFormController.class);

    @Autowired
    private PreferencesRequestResolver preferencesRequestResolver;
    @Resource(name="preferencesStore")
    private PreferencesStore preferencesStore;
    private PreferencesValidator validator = new PreferencesValidator();

    @ModelAttribute
    private Preferences getPreferences(PortletRequest request) {
        return this.preferencesRequestResolver.getPreferences(request);
    }

    @RenderMapping(params = "action=saveOptions")
    public String showOptionsForm(Model model, PortletRequest request) {
        logger.debug("prefs render");
        final Preferences prefs = getPreferences(request);
        logger.debug("prefs (options): {}", prefs);
        model.addAttribute("options", prefs);
        return "editBookmarks";
    }

    @ActionMapping(params = "action=saveOptions")
    public void saveOptions(@ModelAttribute("options") Preferences prefs, BindingResult result,
                            ActionRequest request, ActionResponse response) {
        logger.debug("prefs action");
        // don't save preferences for guest users
        if (request.getRemoteUser() == null) {
            logger.warn("guest user should not be saving options");
            return;
        }

        populatePreferences(prefs, request);
        validator.validate(prefs, result);
        logger.debug("prefs: {}", prefs);
        logger.debug("results: {}", result);

        if (!result.hasErrors()) {
            logger.debug("no errors");
            prefs.setModified(new Date());
            this.preferencesStore.storePreferences(prefs);
            response.setRenderParameter("action", "viewBookmarks");
        } else {
            logger.warn("prefs errors!");
            request.setAttribute(ViewConstants.ERRORS, result);
            response.setRenderParameter("action", "saveOptions");
        }
    }

    private void populatePreferences(Preferences preferences, ActionRequest request) {
        //Update info that should not be modified by form
        final Preferences savedPreferences = getPreferences(request);
        preferences.setId(savedPreferences.getId());
        preferences.setOwner(savedPreferences.getOwner());
        preferences.setName(savedPreferences.getName());
        preferences.setCreated(savedPreferences.getCreated() != null ? savedPreferences.getCreated() : new Date());
        preferences.setModified(savedPreferences.getModified() != null ? savedPreferences.getModified() : new Date());
    }
}
