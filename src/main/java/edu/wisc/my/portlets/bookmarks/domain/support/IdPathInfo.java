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
package edu.wisc.my.portlets.bookmarks.domain.support;

import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>IdPathInfo class.</p>
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12140 $
 */
public class IdPathInfo {
    private final long[] idPath;
    private final Folder parent;
    private final Entry target;
    
    /**
     * <p>Constructor for IdPathInfo.</p>
     *
     * @param indexPath an array of {@link long} objects.
     * @param parent a {@link edu.wisc.my.portlets.bookmarks.domain.Folder} object.
     * @param target a {@link edu.wisc.my.portlets.bookmarks.domain.Entry} object.
     */
    public IdPathInfo(long[] indexPath, Folder parent, Entry target) {
        this.idPath = indexPath;
        this.parent = parent;
        this.target = target;
    }

    /**
     * <p>Getter for the field <code>idPath</code>.</p>
     *
     * @return Returns the idPath.
     */
    public long[] getIdPath() {
        return this.idPath;
    }

    /**
     * <p>Getter for the field <code>parent</code>.</p>
     *
     * @return Returns the parent.
     */
    public Folder getParent() {
        return this.parent;
    }

    /**
     * <p>Getter for the field <code>target</code>.</p>
     *
     * @return Returns the target.
     */
    public Entry getTarget() {
        return this.target;
    }

    /** {@inheritDoc} */
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof IdPathInfo)) {
            return false;
        }
        IdPathInfo rhs = (IdPathInfo)object;
        return new EqualsBuilder()
                .append(this.idPath, rhs.idPath)
                .append(this.target, rhs.target)
                .append(this.parent, rhs.parent)
                .isEquals();
    }

    /**
     * <p>hashCode.</p>
     *
     * @see java.lang.Object#hashCode()
     * @return a int.
     */
    public int hashCode() {
        return new HashCodeBuilder(1559811763, 1812223771)
                .append(this.idPath)
                .append(this.target)
                .append(this.parent)
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
                .append("parent", this.parent)
                .append("idPath", this.idPath)
                .append("target", this.target)
                .toString();
    }
}
