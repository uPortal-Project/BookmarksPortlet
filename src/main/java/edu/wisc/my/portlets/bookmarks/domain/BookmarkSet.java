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
     * @return Returns the owner.
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * @param owner The owner to set.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
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
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-280978973, 410501249).appendSuper(super.hashCode())
                .append(this.owner)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString())
                .append("owner", this.owner)
                .toString();
    }
}
