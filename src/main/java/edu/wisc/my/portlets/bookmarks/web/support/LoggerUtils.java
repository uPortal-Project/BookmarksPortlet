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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletRequest;
import java.util.Collections;

public class LoggerUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    public static void logRequest(final String label, final PortletRequest request) {
        if (!logger.isInfoEnabled()) {
            return;
        }

        logger.info("{} req params: ", label);
        request.getParameterMap().forEach((k,v) -> logger.info("param: {} -> {}", k, v));
        logger.info("{} req props: ", label);
        Collections.list(request.getPropertyNames()).forEach(p -> logger.info("prop: {} -> {}", p, request.getProperty(p)));
        logger.info("{} req attrs: ", label);
        Collections.list(request.getAttributeNames()).forEach(a -> logger.info("prop: {} -> {}", a, request.getAttribute(a)));
    }
}
