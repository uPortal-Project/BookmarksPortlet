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
 * A bookmark tracks a URL and if the URL should be opened in a new window.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12140 $
 */
public class Bookmark extends Entry {
    private static final long serialVersionUID = 1L;
    
    private String url;
    private boolean newWindow = true;

    /**
     * @return Returns the newWindow.
     */
    public boolean isNewWindow() {
        return this.newWindow;
    }

    /**
     * @param newWindow The newWindow to set.
     */
    public void setNewWindow(boolean newWindow) {
        this.newWindow = newWindow;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Bookmark)) {
            return false;
        }
        Bookmark rhs = (Bookmark)object;
        return new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.newWindow, rhs.newWindow)
                .append(this.url, rhs.url)
                .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-406873869, 1835942177).appendSuper(super.hashCode())
                .append(this.newWindow)
                .append(this.url)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString())
                .append("newWindow", this.newWindow)
                .append("url", this.url)
                .toString();
    }
}
