package com.healinghaven.bigmomma.service;

import com.healinghaven.bigmomma.entity.Image;
import com.healinghaven.bigmomma.enums.ImageEntityType;
import com.healinghaven.bigmomma.repository.ImageRepository;
import com.healinghaven.bigmomma.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private static final Logger LOG = LoggerFactory.getLogger(ImageService.class);
    @Autowired
    private ImageRepository repository;

    public List<Image> getAllImages() {
        try {
            LOG.info("Attempting to retrieve all images");
            return repository.getAllImages();
        } catch (Exception e) {
            LOG.error("Failed to get all images", e);
            return null;
        }
    }

    public List<Image> getProductImages (int productID) {
        try {
            LOG.info("Attempting to get images belonging to product with ID[" + productID + "]");
            return repository.getProductImages(productID);
        } catch (Exception e) {
            LOG.error("Failed to get images belonging to entity[" + productID + "]");
            return null;
        }
    }

    public Image saveImage(Image image, int entityID, ImageEntityType imageEntityType) {
        try {
            LOG.info("Attempting to save image[" + image + "]");
            return repository.saveImage(image,entityID, imageEntityType);
        } catch (Exception e) {
            LOG.error("Failed to save image[" + image + "]", e);
            return null;
        }
    }

    public List<Image> saveImages(List<Image> images, int entityID, ImageEntityType imageEntityType) {
        try {
            LOG.info("Attempting to save images[" + images + "]");
            ImageUtil.saveImagesToDirectory(images);
            return repository.saveImages(images, entityID, imageEntityType);
        } catch (Exception e) {
            LOG.error("Failed to save images[" + images + "]", e);
            return null;
        }
    }
    public Image getImageById(int imageId) {
        try {
            LOG.info("Attempting to get image with id[" + imageId + "]");
            return repository.getImageById(imageId);
        } catch (Exception e) {
            LOG.error("Failed to get image with id[" + imageId + "]", e);
            return null;
        }
    }
}
