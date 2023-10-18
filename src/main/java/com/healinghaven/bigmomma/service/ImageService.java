package com.healinghaven.bigmomma.service;

import com.healinghaven.bigmomma.entity.Image;
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

    public List<Image> getEntityImages(int entityID) {
        try {
            LOG.info("Attempting to get images belonging to entity[" + entityID + "]");
            return repository.getEntityImages(entityID);
        } catch (Exception e) {
            LOG.error("Failed to get images belonging to entity[" + entityID + "]");
            return null;
        }
    }

    public Image saveImage(Image image, int entityID) {
        try {
            LOG.info("Attempting to save image[" + image + "]");
            return repository.saveImage(image,entityID);
        } catch (Exception e) {
            LOG.error("Failed to save image[" + image + "]", e);
            return null;
        }
    }

    public List<Image> saveImages(List<Image> images, int entityID) {
        try {
            LOG.info("Attempting to save images[" + images + "]");
            ImageUtil.saveImagesToDirectory(images);
            return repository.saveImages(images, entityID);
        } catch (Exception e) {
            LOG.error("Failed to save images[" + images + "]", e);
            return null;
        }
    }
}
