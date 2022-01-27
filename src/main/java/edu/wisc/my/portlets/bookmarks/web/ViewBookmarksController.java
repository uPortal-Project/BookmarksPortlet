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
/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.RenderRequest;
import java.util.Map;

/**
 * Controller resolves the BookmarkSet owner and name for the request and displays it
 * as the model.
 */
@Controller
@RequestMapping("VIEW")
public class ViewBookmarksController {

    private static final Logger logger = LoggerFactory.getLogger(ViewBookmarksController.class);

    @Autowired
    private ReferenceData referenceData;

    @RenderMapping
    public String addDataForView(Model model, RenderRequest request) {
        logger.debug("Entering VIEW addDataForView()");
        final Map<String, Object> refData = referenceData.getRefData(request, null);
        model.addAllAttributes(refData);
        return "viewBookmarks";
    }
}
