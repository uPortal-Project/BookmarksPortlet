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
package edu.wisc.my.portlets.bookmarks.web.support;

import javax.portlet.PortletRequest;

/**
 * Support interface to resolve the BookmarkSet name for a request.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12161 $
 */
public interface NameResolver {
    
    /**
     * Resolves the BookmarkSet name for the PortletRequest, may return null if no name is specified for the request.
     * 
     * @param request The request to resolve the name for, must not be null.
     * @return The BookmarkSet name for the request, may be null if there is no name for the request.
     */
    public String getBookmarkSetName(PortletRequest request);
}
