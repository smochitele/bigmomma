package com.healinghaven.bigmomma.utils;

import io.micrometer.common.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class EncryptionUtil {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtil.class);
    public static String getHashedSHA256String(String plainString) {
        if(StringUtils.isNotBlank(plainString)) {
            try {
                LOG.info("Attempting to encrypt string from [" + plainString + "] to [" + DigestUtils.sha256Hex(plainString) + "]");
                return DigestUtils.sha256Hex(plainString);
            } catch (Exception e) {
                LOG.error("Failed to encrypt string", e);
                return null;
            }
        } else {
            LOG.warn("Input string empty");
            return plainString;
        }
    }
}
