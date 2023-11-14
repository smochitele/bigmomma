package com.healinghaven.bigmomma.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class ConfigUtil {
    private static Properties properties;

    private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);

    public static String getString(String configConstant) {
        try {
            return Objects.requireNonNull(getProperties()).getProperty(configConstant);
        } catch (Exception e) {
            LOG.error("Failed to get config for[" + configConstant + "]", e);
            return configConstant;
        }
    }

    public static List<Object> getList(String configConstant, String separator) {
        try {
            return Arrays.asList(configConstant.split(separator));
        } catch (Exception e) {
            LOG.error("Failed to derive list from[" + configConstant + "]", e);
            return null;
        }
    }

    private static synchronized Properties getProperties() {
        if (properties != null) {
            return properties;
        }
        try {
            properties = new Properties();
            properties.load(new BufferedInputStream(new FileInputStream(ConfigConstants.PROPERTIES_FILE_DIR)));
            return properties;
        } catch (Exception e) {
            LOG.info("Failed to load properties", e);
            return null;
        }
    }
}
