package com.healinghaven.bigmomma.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseUtil.class);
    public static void close(Connection connection, PreparedStatement query) {
        DatabaseUtil.close( connection, query, null );
    }

    public static void close(Connection connection, PreparedStatement query, ResultSet rs) {
        if( rs != null ) {
            try { rs.close(); } catch(Throwable e) {
                LOG.error("Failed to close connection", e);
            }
        }
        rs = null;
        if( query != null ) {
            try { query.close(); } catch(Throwable e) {
                LOG.error("Failed to close connection", e);
            }
        }
        query = null;
        if( connection != null ) {
            try { connection.close(); } catch(Throwable e) {
                LOG.error("Failed to close connection", e);
            }
        }
        connection = null;
    }
}
