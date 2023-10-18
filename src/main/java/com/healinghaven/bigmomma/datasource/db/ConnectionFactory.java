package com.healinghaven.bigmomma.datasource.db;

import com.healinghaven.bigmomma.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);

    private static final String DB_URL = ConfigUtil.getString("spring.datasource.url");

    private final static String DB_USERNAME = ConfigUtil.getString("spring.datasource.username");

    private final static String DB_PASSWORD = ConfigUtil.getString("spring.datasource.password");

    public static Connection getConnection() {
        try {
            return  DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            LOG.error("Failed to get connection", e);
            throw new RuntimeException(e);
        }
    }
}
