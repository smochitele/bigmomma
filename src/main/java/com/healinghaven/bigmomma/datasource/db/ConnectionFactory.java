package com.healinghaven.bigmomma.datasource.db;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private ConnectionFactory() {

    }
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);
    @Autowired
    private static Environment environment;

    private final static String DB_URL = "jdbc:mysql://localhost:3306/momma_db";//environment.getProperty("spring.datasource.url");

    private final static String DB_USERNAME = "root";//environment.getProperty("spring.datasource.username");

    private final static String DB_PASSWORD = "password";//environment.getProperty("spring.datasource.password");

    public static Connection getConnection() {
        try {
            return  DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            LOG.error("Failed to get connection", e);
            throw new RuntimeException(e);
        }
    }
}
