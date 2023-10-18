package com.healinghaven.bigmomma.utils;

import com.healinghaven.bigmomma.entity.Image;
import org.hibernate.id.Configurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ImageUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);
    public static void setBase64StringToImage(List<Image> images) {
        if(images != null && images.size() > 0) {
            for (Image image : images) {
                try {
                    File file = new File(image.getLocation());
                    byte[] input = Files.readAllBytes(Paths.get(file.toURI()));
                    byte[] encodedBytes = Base64.getEncoder().encode(input);
                    image.setBase64String(new String(encodedBytes));
                } catch (Exception e) {
                    LOG.error("Failed to set base64 string for image name[" + image.getLocation() + "] with id[" + image.getId() + "]", e);
                }
            }
        }
    }

    public static void saveImageToDirectory(Image image) {
        if (image != null) {
            try {
                byte[] data = Base64.getDecoder().decode(image.getBase64String());
                try (OutputStream stream = new FileOutputStream(ConfigUtil.getString(ConfigConstants.IMAGE_FILE_LOCATION + getGeneratedImageName(image)))) {
                    stream.write(data);
                }
            } catch (Exception e) {
                LOG.error("Failed to create image from base64", e);
            }
        }
    }

    public static void saveImagesToDirectory(List<Image> images) {
        if (images != null) {
            try {
                LOG.info("Saving images to the[" + ConfigConstants.IMAGE_FILE_LOCATION + "] directory");
                for (Image i : images){
                    byte[] data = Base64.getDecoder().decode(i.getBase64String());
                    try (OutputStream stream = new FileOutputStream(ConfigUtil.getString(ConfigConstants.IMAGE_FILE_LOCATION) + getGeneratedImageName(i))) {
                        LOG.info("Writing data[" + i.getBase64String() + "] to [" + ConfigUtil.getString(ConfigConstants.IMAGE_FILE_LOCATION) + getGeneratedImageName(i) + "]");
                        stream.write(data);
                    } catch (Exception e) {
                        LOG.error("Failed to save image[" + i + "] to directory[" + ConfigConstants.IMAGE_FILE_LOCATION + "]", e);
                    }
                }
            } catch (Exception e) {
                LOG.error("Failed to create image from base64", e);
            }
        }
    }

    private static String getGeneratedImageName(Image image) {
        try {
            return image != null && image.getImageName() != null ? System.currentTimeMillis() + "_" + image.getImageName() : String.valueOf(System.currentTimeMillis());
        } catch (Exception e) {
            LOG.error("Failed to generate image name", e);
            assert image != null;
            return image.getImageName();
        }
    }
}
