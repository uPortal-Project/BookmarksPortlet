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
package edu.wisc.my.portlets.bookmarks.dao.hibernate.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Base class for implementing a UserType to persist an Enumeration
 *
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12179 $
 */
public abstract class EnumUserType<E extends Enum<E>> implements UserType {
    private static final int[] SQL_TYPES = { Types.VARCHAR };
    private final Class<E> clazz;

    /**
     * <p>Constructor for EnumUserType.</p>
     *
     * @param c a {@link java.lang.Class} object.
     */
    protected EnumUserType(Class<E> c) {
        this.clazz = c;
    }

    
    /**
     * <p>sqlTypes.</p>
     *
     * @return an array of {@link int} objects.
     */
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    /**
     * <p>returnedClass.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    public Class returnedClass() {
        return clazz;
    }

    /** {@inheritDoc} */
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException {
        final String name = resultSet.getString(names[0]);

        final E result;
        if (!resultSet.wasNull()) {
            result = Enum.valueOf(clazz, name);
        }
        else {
            result = null;
        }
        
        return result;
    }

    /** {@inheritDoc} */
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(index, Types.VARCHAR);
        }
        else {
            preparedStatement.setString(index, ((Enum)value).name());
        }
    }

    /** {@inheritDoc} */
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /**
     * <p>isMutable.</p>
     *
     * @return a boolean.
     */
    public boolean isMutable() {
        return false;
    }

    /** {@inheritDoc} */
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    /** {@inheritDoc} */
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    /** {@inheritDoc} */
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    /** {@inheritDoc} */
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    /** {@inheritDoc} */
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }

        if (null == x || null == y) {
            return false;
        }

        return x.equals(y);
    }
}
