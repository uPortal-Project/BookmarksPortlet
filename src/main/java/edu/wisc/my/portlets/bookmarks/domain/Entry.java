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
     * @return Returns the note.
     */
    public String getNote() {
        return this.note;
    }

    /**
     * @param note The note to set.
     */
    public void setNote(String note) {
        this.note = note;
    }

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
    
    /**
     * @see java.lang.Object#equals(Object)
     */
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
     * @see java.lang.Object#hashCode()
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
     * @see java.lang.Object#toString()
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
