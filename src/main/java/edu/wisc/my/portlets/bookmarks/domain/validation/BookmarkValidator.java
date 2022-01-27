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
package edu.wisc.my.portlets.bookmarks.domain.validation;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.wisc.my.portlets.bookmarks.domain.Bookmark;

/**
 * Validates a Bookmark, requires a valid URL, determined by calling new URL on the URL String. Also
 * appends a default protocol of http:// if it isn't already there. The default protocol the validator
 * uses may be set via the defaultProtocol property.
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12165 $
 */
public class BookmarkValidator extends EntryValidator {
    private static final String PROTOCOL_SEPERATOR = "://";


    private String defaultProtocol = "http://";

    /**
     * <p>Getter for the field <code>defaultProtocol</code>.</p>
     *
     * @return Returns the defaultProtocol.
     */
    public String getDefaultProtocol() {
        return this.defaultProtocol;
    }

    /**
     * <p>Setter for the field <code>defaultProtocol</code>.</p>
     *
     * @param defaultProtocol The defaultProtocol to set.
     */
    public void setDefaultProtocol(String defaultProtocol) {
        if (defaultProtocol == null) {
            throw new IllegalArgumentException("defaultProtocol may not be null.");
        }
        if (!defaultProtocol.endsWith(PROTOCOL_SEPERATOR)) {
            throw new IllegalArgumentException("defaultProtocol must end with a the protocol seperator='" + PROTOCOL_SEPERATOR + "'.");
        }

        this.defaultProtocol = defaultProtocol;
    }

    /** {@inheritDoc} */
    @Override
    public boolean supports(Class clazz) {
        return Bookmark.class.isAssignableFrom(clazz) && super.supports(clazz);
    }

    /** {@inheritDoc} */
    @Override
    public void validate(Object obj, Errors errors) {
        super.validate(obj, errors);
        final Bookmark bookmark = (Bookmark)obj;
        this.validateUrl(bookmark, errors);
    }

    private void validateUrl(Bookmark bookmark, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "portlet.bookmark.error.url.required");

        String url = bookmark.getUrl();
        if (!url.contains(PROTOCOL_SEPERATOR)) {
            url = this.defaultProtocol + url;
        }

        bookmark.setUrl(url);

        try {
            new URL(url);
        }
        catch (MalformedURLException mue) {
            errors.rejectValue("url", "portlet.bookmark.error.url.malformed");
        }
    }
}
