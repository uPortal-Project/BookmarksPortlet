/* Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
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
