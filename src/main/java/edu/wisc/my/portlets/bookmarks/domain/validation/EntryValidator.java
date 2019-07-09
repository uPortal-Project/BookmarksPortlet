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

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.wisc.my.portlets.bookmarks.domain.Entry;

/**
 * Validates an entry, requires a non null/empty name.
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12165 $
 */
public class EntryValidator implements Validator {

    /** {@inheritDoc} */
    public boolean supports(Class clazz) {
        return Entry.class.isAssignableFrom(clazz);
    }

    /** {@inheritDoc} */
    public void validate(Object obj, Errors errors) {
        final Entry entry = (Entry)obj;
        this.validateName(entry, errors);
    }

    private void validateName(Entry entry, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "portlet.entry.error.name.required");
    }
}
