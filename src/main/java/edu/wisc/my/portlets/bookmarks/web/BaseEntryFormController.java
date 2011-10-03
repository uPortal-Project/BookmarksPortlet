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

package edu.wisc.my.portlets.bookmarks.web;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.validation.BindException;

/**
 * Takes the responsibility of copying entry parameters that aren't bound to the command from
 * the request to the response. Sub classes MUST call super.processFormSubmission if they
 * override {@link #processFormSubmission(ActionRequest, ActionResponse, Object, BindException)}. 
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12164 $
 */
public class BaseEntryFormController extends BaseBookmarksFormController {

    /**
     * @see org.springframework.web.portlet.mvc.SimpleFormController#processFormSubmission(javax.portlet.ActionRequest, javax.portlet.ActionResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected void processFormSubmission(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
        if (errors.hasErrors()) {
            final String action = request.getParameter("action");
            final String idPath = request.getParameter("idPath");
            final String folderPath = request.getParameter("folderPath");

            response.setRenderParameter("action", action);
            response.setRenderParameter("idPath", idPath);
            response.setRenderParameter("folderPath", folderPath);
        }
        
        super.processFormSubmission(request, response, command, errors);
    }
}
