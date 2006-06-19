/*******************************************************************************
 * Copyright 2006, The Board of Regents of the University of Wisconsin System.
 * All rights reserved.
 *
 * A non-exclusive worldwide royalty-free license is granted for this Software.
 * Permission to use, copy, modify, and distribute this Software and its
 * documentation, with or without modification, for any purpose is granted
 * provided that such redistribution and use in source and binary forms, with or
 * without modification meets the following conditions:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *c
 * 3. Redistributions of any form whatsoever must retain the following
 * acknowledgement:
 *
 * "This product includes software developed by The Board of Regents of
 * the University of Wisconsin System."
 *
 *THIS SOFTWARE IS PROVIDED BY THE BOARD OF REGENTS OF THE UNIVERSITY OF
 *WISCONSIN SYSTEM "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING,
 *BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE BOARD OF REGENTS OF
 *THE UNIVERSITY OF WISCONSIN SYSTEM BE LIABLE FOR ANY DIRECT, INDIRECT,
 *INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 *OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package edu.wisc.my.portlets.bookmarks.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import edu.wisc.my.portlets.bookmarks.domain.support.IntegerSetThreadLocal;

/**
 * A Folder can contain other entries in a List. This is a basic bean and no defensive copying
 * is done. Changes made to List returned by {@link #getChildren()} will be reflected to other
 * code using these APIs.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision$
 */
public class Folder extends Entry {
    private static IntegerSetThreadLocal equalsVisitedFolder = new IntegerSetThreadLocal();
    private static IntegerSetThreadLocal hashCodeVisitedFolder = new IntegerSetThreadLocal();
    private static IntegerSetThreadLocal toStringVisitedFolder = new IntegerSetThreadLocal();

    private List<Entry> children = new ArrayList<Entry>();
    private boolean minimized = false;


    /**
     * @return Returns the children.
     */
    public List<Entry> getChildren() {
        return this.children;
    }

    /**
     * @param children The children to set.
     */
    public void setChildren(List<Entry> children) {
        if (!(children instanceof RandomAccess)) {
            this.logger.warn("Children list '" + children.getClass() + "' is not a RandomAccess list, performance will be degraded.");
        }
        
        this.children = children;
    }

    /**
     * @return Returns the minimized.
     */
    public boolean isMinimized() {
        return this.minimized;
    }

    /**
     * @param minimized The minimized to set.
     */
    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        final Set<Integer> visited = equalsVisitedFolder.getSet();
        final int identityHash = System.identityHashCode(this);
        try {
            if (!visited.add(identityHash)) {
                visited.clear();
                throw new IllegalStateException("A loop exists in the Folder tree.");
            }
        
            if (object == this) {
                return true;
            }
            if (!(object instanceof Folder)) {
                return false;
            }
            Folder rhs = (Folder)object;
            return new EqualsBuilder().appendSuper(super.equals(object))
                    .append(this.children, rhs.children)
                    .append(this.minimized, rhs.minimized)
                    .isEquals();
        }
        finally {
            visited.remove(identityHash);
        }
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final Set<Integer> visited = hashCodeVisitedFolder.getSet();
        final int identityHash = System.identityHashCode(this);
        try {
            if (!visited.add(identityHash)) {
                visited.clear();
                throw new IllegalStateException("A loop exists in the Folder tree.");
            }
        
            return new HashCodeBuilder(-409984457, 961354191).appendSuper(super.hashCode())
                    .append(this.children)
                    .append(this.minimized)
                    .toHashCode();
        }
        finally {
            visited.remove(identityHash);
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        final Set<Integer> visited = toStringVisitedFolder.getSet();
        final int identityHash = System.identityHashCode(this);
        try {
            if (!visited.add(identityHash)) {
                visited.clear();
                throw new IllegalStateException("A loop exists in the Folder tree.");
            }
        
            return new ToStringBuilder(this).appendSuper(super.toString())
                    .append("children", this.children)
                    .append("minimized", this.minimized)
                    .toString();
        }
        finally {
            visited.remove(identityHash);
        }
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.domain.Entry#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Object object) {
        if (object instanceof Bookmark) {
            return -1;
        }
        else {
            return super.compareTo(object);
        }
    }
}
