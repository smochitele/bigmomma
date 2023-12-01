package com.healinghaven.bigmomma.utils;

import com.healinghaven.bigmomma.entity.Image;
import com.healinghaven.bigmomma.entity.Product;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ImageUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);
    public static void setBase64StringToImages(List<Image> images) {
        if(images != null && images.size() > 0) {
            for (Image image : images) {
                try {
                    LOG.info("Getting base64 data for image[" + image + "]");
                    File file = new File(image.getLocation());
                    byte[] input = Files.readAllBytes(file.toPath());
                    byte[] encodedBytes = Base64.getEncoder().encode(input);
                    image.setBase64String(new String(encodedBytes));
                } catch (Exception e) {
                    LOG.error("Failed to set base64 string for image name[" + image.getLocation() + "] with id[" + image.getId() + "]", e);
                }
            }
        }
    }

    public static void setBase64StringToProductImages(List<Product> products) {
        for(Product p : products) {
            setBase64StringToImages(p.getImages());
        }
    }

    public static void setBase64StringToImages(Image image) {
        if(image != null) {
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

    public static void saveImageToDirectory(Image image) {
        if (image != null && StringUtils.isNotBlank(image.getBase64String())){
            try {
                byte[] data = Base64.getDecoder().decode(image.getBase64String());
                try (OutputStream stream = new FileOutputStream(ConfigUtil.getString(ConfigConstants.IMAGE_FILE_LOCATION) + getGeneratedImageName(image))) {
                    stream.write(data);
                }
            } catch (Exception e) {
                LOG.error("Failed to create image from base64", e);
            }
        } else {
            LOG.warn("Null image passed in method[public static void saveImageToDirectory(Image image)]");
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

    private static synchronized String getGeneratedImageName(Image image) {
        String imageName;
        if (image != null && StringUtils.isNotBlank(image.getImageName())) {
            try {
                imageName = generateImageName(image);
                LOG.info("Image[" + image + "] has been assigned image name[" + imageName + "]");
                return imageName;
            } catch (Exception e) {
                LOG.error("Failed to generate image name", e);
                return image.getImageName();
            }
        } else {
            imageName = generateImageName(image);
            LOG.warn("Null image passed in method[private static synchronized String getGeneratedImageName(Image image)], now gonna assign default image name[" + imageName + "]");
            return imageName;
        }
    }

    private static String generateImageName(Image image) {
        if(image != null && StringUtils.isNotBlank(image.getImageName()))
            return System.currentTimeMillis() + "_" + UUID.randomUUID() +  "_" + image.getImageName();
        else
            return System.currentTimeMillis() + "_" + UUID.randomUUID();
    }
}
