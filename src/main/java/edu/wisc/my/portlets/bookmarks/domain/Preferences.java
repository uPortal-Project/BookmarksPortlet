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

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class contains preferences (mainly user interface/experiance related) for a BookmarkSet.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12179 $
 */
public class Preferences implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum DefaultFolderOperation {
        OPENED, 
        CLOSED, 
        SAVED;
    }
    
    private long id = -1;
    private String name;
    private String owner;
    private Date created;
    private Date modified;
    
    private DefaultFolderOperation defaultFolderOperation = DefaultFolderOperation.CLOSED;


    /**
     * @return Returns the id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

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
     * @return Returns the created.
     */
    public Date getCreated() {
        return this.created;
    }

    /**
     * @param created The created to set.
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return Returns the modified.
     */
    public Date getModified() {
        return this.modified;
    }

    /**
     * @param modified The modified to set.
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return Returns the defaultFolderOperation.
     */
    public DefaultFolderOperation getDefaultFolderOperation() {
        return this.defaultFolderOperation;
    }

    /**
     * @param defaultFolderOperation The defaultFolderOperation to set.
     */
    public void setDefaultFolderOperation(DefaultFolderOperation defaultFolderOperation) {
        if (defaultFolderOperation == null) {
            this.defaultFolderOperation = DefaultFolderOperation.CLOSED;
        }
        else {
            this.defaultFolderOperation = defaultFolderOperation;
        }
    }
    
    /**
     * @return The array of possible DefaultFolderOperations
     */
    public DefaultFolderOperation[] getDefaultFolderOperations() {
        return DefaultFolderOperation.values();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Preferences)) {
            return false;
        }
        Preferences rhs = (Preferences)object;
        return new EqualsBuilder()
            .append(this.id, rhs.id)
            .append(this.name, rhs.name)
            .append(this.owner, rhs.owner)
            .append(this.created, rhs.created)
            .append(this.modified, rhs.modified)
            .append(this.defaultFolderOperation, rhs.defaultFolderOperation)
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1282633449, -470755709)
            .append(this.id)
            .append(this.name)
            .append(this.owner)
            .append(this.created)
            .append(this.modified)
            .append(this.defaultFolderOperation)
            .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", this.id)
            .append("name", this.name)
            .append("owner", this.owner)
            .append("created", this.created)
            .append("modified", this.modified)
            .append("defaultFolderOperation", this.defaultFolderOperation)
            .toString();
    }
}
