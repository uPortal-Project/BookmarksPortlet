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

import edu.wisc.my.portlets.bookmarks.domain.CollectionFolder;

public class CollectionValidator  extends EntryValidator {

    /**
     * @see edu.wisc.my.portlets.bookmarks.domain.validation.EntryValidator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class clazz) {
        return CollectionFolder.class.isAssignableFrom(clazz) && super.supports(clazz);
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.domain.validation.EntryValidator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object obj, Errors errors) {
        super.validate(obj, errors);
        final CollectionFolder collection = (CollectionFolder)obj;
        this.validateUrl(collection, errors);
    }

    private void validateUrl(CollectionFolder collection, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "portlet.bookmark.error.url.required");

        String url = collection.getUrl();
        collection.setUrl(url);

        try {
            new URL(url);
        }
        catch (MalformedURLException mue) {
            errors.rejectValue("url", "portlet.bookmark.error.url.malformed");
        }
    }
}
