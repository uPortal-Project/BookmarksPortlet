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
package edu.wisc.my.taglibs;

import javax.portlet.WindowState;

/**
 * <p>Functions class.</p>
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12171 $
 */
public class Functions {
    /**
     * <p>instanceOf.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param className a {@link java.lang.String} object.
     * @return a boolean.
     * @throws java.lang.ClassNotFoundException if any.
     */
    public static boolean instanceOf(Object obj, String className) throws ClassNotFoundException {
        final ClassLoader cl = obj.getClass().getClassLoader();
        final Class clazz = Class.forName(className, true, cl);
        final boolean isInstanceOf = obj.getClass().isAssignableFrom(clazz);
        return isInstanceOf;
    }
    
    /**
     * <p>isState.</p>
     *
     * @param state a {@link javax.portlet.WindowState} object.
     * @param name a {@link java.lang.String} object.
     * @return a boolean.
     */
    public static boolean isState(WindowState state, String name) {
        return (state == null && name == null) || (state != null && state.toString().equalsIgnoreCase(name));
    }
}
