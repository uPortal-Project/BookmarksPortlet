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

package edu.wisc.my.portlets.bookmarks.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * When the {@link #close()} is called the configured SQL will be run on
 * the configured DataSource.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class SqlShutdownHelper extends JdbcDaoSupport {
    private String shutdownSql = "SHUTDOWN COMPACT";
    
    public String getShutdownSql() {
        return shutdownSql;
    }
    public void setShutdownSql(String shutdownSql) {
        this.shutdownSql = shutdownSql;
    }

    public void close() {
        final JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        jdbcTemplate.execute(this.shutdownSql);
        this.logger.info("Executed '" + this.shutdownSql + "'");
    }
}
