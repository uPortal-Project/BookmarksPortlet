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
/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.web;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.SimpleFormController;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.Bookmark;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.web.support.BookmarkSetRequestResolver;

/**
 * Controller resolves the BookmarkSet owner and name for the request and displays it
 * as the model.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class ViewBookmarksController extends SimpleFormController {
    public static final String BOOKMARK_SET = "bookmarkSet";
    
    protected BookmarkStore bookmarkStore;
    protected BookmarkSetRequestResolver bookmarkSetRequestResolver;
    
    /**
     * @return Returns the bookmarkSetRequestResolver.
     */
    public BookmarkSetRequestResolver getBookmarkSetRequestResolver() {
        return this.bookmarkSetRequestResolver;
    }

    /**
     * @param bookmarkSetRequestResolver The bookmarkSetRequestResolver to set.
     */
    public void setBookmarkSetRequestResolver(BookmarkSetRequestResolver bookmarkSetRequestResolver) {
        this.bookmarkSetRequestResolver = bookmarkSetRequestResolver;
    }

    /**
     * @return Returns the bookmarkStore.
     */
    public BookmarkStore getBookmarkStore() {
        return this.bookmarkStore;
    }

    /**
     * @param bookmarkStore The bookmarkStore to set.
     */
    public void setBookmarkStore(BookmarkStore bookmarkStore) {
        this.bookmarkStore = bookmarkStore;
    }

    
    
    /**
     * @see org.springframework.web.portlet.mvc.SimpleFormController#referenceData(javax.portlet.PortletRequest, java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    protected Map referenceData(PortletRequest request, Object command, Errors errors) throws Exception {
        final BookmarkSet bookmarkSet = this.bookmarkSetRequestResolver.getBookmarkSet(request);
        
        final Map<String, Object> refData = new HashMap<String, Object>();
        refData.put(BOOKMARK_SET, bookmarkSet);
        refData.put("emptyCommand", new Bookmark());
        
        return refData;
    }

    /**
     * @see org.springframework.web.portlet.mvc.SimpleFormController#renderFormSubmission(javax.portlet.RenderRequest, javax.portlet.RenderResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected ModelAndView renderFormSubmission(RenderRequest request, RenderResponse response, Object command, BindException errors) throws Exception {
        //This should ensure the form renders correctly every time
        return showForm(request, response, errors);
    }
}
