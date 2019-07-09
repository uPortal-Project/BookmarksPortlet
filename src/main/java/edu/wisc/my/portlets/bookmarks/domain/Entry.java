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
 * An Entry is the base object that may in a bookmarks hierarchy. It is a stand alone object
 * but is indended to be extended.
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12141 $
 */
public class Entry implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long id = -1;
    private String name;
    private String note;
    private Date created = new Date();
    private Date modified = this.created;

    /**
     * <p>Getter for the field <code>created</code>.</p>
     *
     * @return Returns the created.
     */
    public Date getCreated() {
        return this.created;
    }

    /**
     * <p>Setter for the field <code>created</code>.</p>
     *
     * @param created The created to set.
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * <p>Getter for the field <code>note</code>.</p>
     *
     * @return Returns the note.
     */
    public String getNote() {
        return this.note;
    }

    /**
     * <p>Setter for the field <code>note</code>.</p>
     *
     * @param note The note to set.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return Returns the id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id The id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>modified</code>.</p>
     *
     * @return Returns the modified.
     */
    public Date getModified() {
        return this.modified;
    }

    /**
     * <p>Setter for the field <code>modified</code>.</p>
     *
     * @param modified The modified to set.
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * <p>getNoteLines.</p>
     *
     * @return A String[] of the lines the note contains
     */
    public String[] getNoteLines() {
        if (this.note == null) {
            return null;
        }
        else {
            return this.note.split("\n");
        }
    }
    
    /** {@inheritDoc} */
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry rhs = (Entry)object;
        return new EqualsBuilder()
                .append(this.name, rhs.name)
                .append(this.note, rhs.note)
                .append(this.created, rhs.created)
                .append(this.modified, rhs.modified)
                .isEquals();
    }

    /**
     * <p>hashCode.</p>
     *
     * @see java.lang.Object#hashCode()
     * @return a int.
     */
    public int hashCode() {
        return new HashCodeBuilder(1323836787, 1770312237)
                .append(this.name)
                .append(this.note)
                .append(this.created)
                .append(this.modified)
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
                .append("id", this.id)
                .append("name", this.name)
                .append("note", this.note)
                .append("created", this.created)
                .append("modified", this.modified)
                .toString();
    }
}
