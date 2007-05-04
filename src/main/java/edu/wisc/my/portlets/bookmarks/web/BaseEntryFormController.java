/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
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
