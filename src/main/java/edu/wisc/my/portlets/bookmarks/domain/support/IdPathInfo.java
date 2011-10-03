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
package edu.wisc.my.portlets.bookmarks.domain.support;

import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12140 $
 */
public class IdPathInfo {
    private final long[] idPath;
    private final Folder parent;
    private final Entry target;
    
    public IdPathInfo(long[] indexPath, Folder parent, Entry target) {
        this.idPath = indexPath;
        this.parent = parent;
        this.target = target;
    }

    /**
     * @return Returns the idPath.
     */
    public long[] getIdPath() {
        return this.idPath;
    }

    /**
     * @return Returns the parent.
     */
    public Folder getParent() {
        return this.parent;
    }

    /**
     * @return Returns the target.
     */
    public Entry getTarget() {
        return this.target;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
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
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1559811763, 1812223771)
                .append(this.idPath)
                .append(this.target)
                .append(this.parent)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString())
                .append("parent", this.parent)
                .append("idPath", this.idPath)
                .append("target", this.target)
                .toString();
    }
}
