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
package edu.wisc.my.portlets.bookmarks.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The root folder for a tree of bookmarks, adds an owner field to the object to associate
 * the set with a user.
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12140 $
 */
public class BookmarkSet extends Folder {
    private static final long serialVersionUID = 1L;
    
    private String owner;

    /**
     * <p>Getter for the field <code>owner</code>.</p>
     *
     * @return Returns the owner.
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * <p>Setter for the field <code>owner</code>.</p>
     *
     * @param owner The owner to set.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /** {@inheritDoc} */
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof BookmarkSet)) {
            return false;
        }
        BookmarkSet rhs = (BookmarkSet)object;
        return new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.owner, rhs.owner)
                .isEquals();
    }

    /**
     * <p>hashCode.</p>
     *
     * @see java.lang.Object#hashCode()
     * @return a int.
     */
    public int hashCode() {
        return new HashCodeBuilder(-280978973, 410501249).appendSuper(super.hashCode())
                .append(this.owner)
                .toHashCode();
    }

    /**
     * <p>toString.</p>
     *
     * @see java.lang.Object#toString()
     * @return a {@link java.lang.String} object.
     */
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString())
                .append("owner", this.owner)
                .toString();
    }
}
