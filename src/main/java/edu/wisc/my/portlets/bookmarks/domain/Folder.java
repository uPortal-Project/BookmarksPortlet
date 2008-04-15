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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import edu.wisc.my.portlets.bookmarks.domain.compare.DefaultBookmarksComparator;
import edu.wisc.my.portlets.bookmarks.domain.support.IntegerSetThreadLocal;

/**
 * A Folder can contain other entries in a List. This is a basic bean and no defensive copying
 * is done. Changes made to List returned by {@link #getChildren()} will be reflected to other
 * code using these APIs.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12155 $
 */
public class Folder extends Entry implements CollapsibleEntry {
    private static final long serialVersionUID = 1L;
    
    private static IntegerSetThreadLocal equalsVisitedFolder = new IntegerSetThreadLocal();
    private static IntegerSetThreadLocal hashCodeVisitedFolder = new IntegerSetThreadLocal();
    private static IntegerSetThreadLocal toStringVisitedFolder = new IntegerSetThreadLocal();

    private Map<Long, Entry> children;
    private transient Comparator<Entry> childComparator = DefaultBookmarksComparator.DEFAULT_BOOKMARKS_COMPARATOR;
    private boolean minimized = false;


    /**
     * @return Returns the children, will never return null.
     */
    public synchronized Map<Long, Entry> getChildren() {
        if (this.children == null) {
            this.children = new HashMap<Long, Entry>();
        }

        return this.children;
    }

    /**
     * @param children The children to set.
     */
    public void setChildren(Map<Long, Entry> children) {
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
     * Returns an immutable sorted view of the values of the children Map. The sorting is done
     * using the current childComparator. Warning, this is has a time cost of 2n log(n)
     * on every call.
     * 
     * @return An immutable sorted view of the folder's children.
     */
    public List<Entry> getSortedChildren() {
        if (this.children == null) {
            return null;
        }
        else {
            final Collection<Entry> childCollection = this.children.values();
            final List<Entry> childList = new ArrayList<Entry>(childCollection);
            Collections.sort(childList, this.childComparator);
            return Collections.unmodifiableList(childList);
        }
    }
    
    /**
     * @return Returns the childComparator.
     */
    public Comparator<Entry> getChildComparator() {
        return this.childComparator;
    }

    /**
     * @param childComparator The childComparator to set, calls setChildComparator on all children of type Folder.
     */
    public void setChildComparator(Comparator<Entry> childComparator) {
        if (childComparator == null) {
            throw new IllegalArgumentException("childComparator cannot be null");
        }
        
        this.childComparator = childComparator;
        
        if (this.children != null) {
            //TODO loop detection?
            for (Map.Entry<Long, Entry> entry : this.children.entrySet()) {
                final Entry child = entry.getValue();
    
                if (child instanceof Folder) {
                    ((Folder)child).setChildComparator(childComparator);
                }
            }
        }
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
    
    
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        
        if (this.childComparator instanceof Serializable) {
            out.writeObject(this.childComparator);
        }
        else {
            out.writeObject(this.childComparator.getClass());
        }
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        
        final Object comparatorInfo = in.readObject();
        if (comparatorInfo instanceof Comparator) {
            this.childComparator = this.castComparator(comparatorInfo);
        }
        else if (comparatorInfo instanceof Class && Comparator.class.isAssignableFrom((Class)comparatorInfo)) {
            try {
                final Object instance = ((Class)comparatorInfo).newInstance();
                this.childComparator = this.castComparator(instance);
            }
            catch (InstantiationException e) {
                this.childComparator = DefaultBookmarksComparator.DEFAULT_BOOKMARKS_COMPARATOR;
            }
            catch (IllegalAccessException e) {
                this.childComparator = DefaultBookmarksComparator.DEFAULT_BOOKMARKS_COMPARATOR;
            }
        }
        else {
            this.childComparator = DefaultBookmarksComparator.DEFAULT_BOOKMARKS_COMPARATOR;
        }
    }
    
    /**
     * Casts an Object to a Comparator<Entry>. Allows the warning suppression to be as localized
     * as possible.
     */
    @SuppressWarnings("unchecked")
    private Comparator<Entry> castComparator(Object obj) {
        return (Comparator<Entry>)obj;
    }
}
