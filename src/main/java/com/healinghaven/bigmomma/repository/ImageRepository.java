package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.Image;
import com.healinghaven.bigmomma.enums.ImageEntityType;
import com.healinghaven.bigmomma.utils.DatabaseUtil;
import com.healinghaven.bigmomma.utils.DateUtil;
import com.healinghaven.bigmomma.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImageRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ImageRepository.class);

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public List<Image> getAllImages() {
        try {
            ArrayList<Image> images = new ArrayList<>();
            final String SQL = "SELECT * FROM momma_db.images";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);

            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if(resultSet.getBoolean("is_active")) {
                    Image image = new Image();

                    image.setId(resultSet.getInt("id"));
                    image.setImageName(resultSet.getString("name_col"));
                    image.setLocation(resultSet.getString("url"));
                    image.setFileExtension(resultSet.getString("extension"));
                    image.setSize(resultSet.getDouble("size_in_bytes"));
                    image.setDateAdded(resultSet.getString("date_added"));
                    image.setImageEntityType(ImageEntityType.getImageEntityType(resultSet.getInt("entity_type")));

                    images.add(image);
                }
            }
            LOG.info("Returned a list of images with size[" + images.size() + "]");
            return images;
        } catch (Exception e) {
            LOG.error("Failed to get all images", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public List<Image> getProductImages (int productId) {
        try {
            ArrayList<Image> images = new ArrayList<>();
            final String SQL = "SELECT * FROM momma_db.images WHERE entity_id = ?";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, productId);

            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if(resultSet.getBoolean("is_active") && ImageEntityType.getImageEntityType(resultSet.getInt("entity_type")).equals(ImageEntityType.PRODUCT_IMAGE)) {
                    Image image = new Image();

                    image.setId(resultSet.getInt("id"));
                    image.setImageName(resultSet.getString("name_col"));
                    image.setLocation(resultSet.getString("url"));
                    image.setFileExtension(resultSet.getString("extension"));
                    image.setSize(resultSet.getDouble("size_in_bytes"));
                    image.setDateAdded(resultSet.getString("date_added"));
                    image.setImageEntityType(ImageEntityType.PRODUCT_IMAGE);

                    images.add(image);
                }
            }
            LOG.info("Returned a list of images with size[" + images.size() + "] for entity[" + productId + "]");
            return images;
        } catch (Exception e) {
            LOG.error("Failed to get images belonging to entity[" + productId +"]", e);
            return new ArrayList<>();
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public Image getVendorLogoImage(int vendorId) {
        try {
            ArrayList<Image> images = new ArrayList<>();
            final String SQL = "SELECT * FROM momma_db.images WHERE entity_id = ?";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, vendorId);

            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();

            Image logo = null;
            if (resultSet.next()) {
                if(resultSet.getBoolean("is_active") && ImageEntityType.getImageEntityType(resultSet.getInt("entity_type")).equals(ImageEntityType.LOGO_IMAGE)) {
                    logo = new Image();

                    logo.setId(resultSet.getInt("id"));
                    logo.setImageName(resultSet.getString("name_col"));
                    logo.setLocation(resultSet.getString("url"));
                    logo.setFileExtension(resultSet.getString("extension"));
                    logo.setSize(resultSet.getDouble("size_in_bytes"));
                    logo.setDateAdded(resultSet.getString("date_added"));
                    logo.setImageEntityType(ImageEntityType.LOGO_IMAGE);
                }
            }
            LOG.info("Returning image[" + logo + "] for vendor[" + vendorId + "]");
            return logo;
        } catch (Exception e) {
            LOG.error("Failed to get image belonging to vendor[" + vendorId +"]", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public Image saveImage(Image image, int entityId) {
        if(image != null) {
            try {
                final String SQL = "INSERT INTO momma_db.images (name_col, url, extension, size_in_bytes, date_added, entity_id) VALUES(?, ?, ?, ?, ?, ?)";

                connection = ConnectionFactory.getConnection();
                LOG.info("Executing query[" + SQL + "]");
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, image.getImageName());
                preparedStatement.setString(2, image.getLocation());
                preparedStatement.setString(3, image.getFileExtension());
                preparedStatement.setDouble(4, image.getSize());
                preparedStatement.setString(5, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
                preparedStatement.setInt(6, entityId);

                preparedStatement.execute();

                LOG.info("Successfully saved image[" + image + "]");

                return image;
            } catch (Exception e) {
                LOG.error("Failed to save image[" + image + "]", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        }
        LOG.error("Passed value in [public Image saveImage(Image image)] is [NULL]");
        return null;
    }


    public Image saveImage(Image image, int entityId, ImageEntityType imageEntityType) {
        if(image != null && !Objects.equals(image.getBase64String(), "")) {
            try {
                final String SQL = "INSERT INTO momma_db.images (name_col, url, extension, size_in_bytes, date_added, entity_type, entity_id) VALUES(?, ?, ?, ?, ?, ?,?)";

                connection = ConnectionFactory.getConnection();
                LOG.info("Executing query[" + SQL + "]");
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, image.getImageName());
                preparedStatement.setString(2, image.getLocation());
                preparedStatement.setString(3, image.getFileExtension());
                preparedStatement.setDouble(4, image.getSize());
                preparedStatement.setString(5, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
                preparedStatement.setInt(6, imageEntityType.ordinal());
                preparedStatement.setInt(7, entityId);
                preparedStatement.execute();

                LOG.info("Successfully saved image[" + image + "]");

                return image;
            } catch (Exception e) {
                LOG.error("Failed to save image[" + image + "]", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        }
        LOG.error("Passed value in [public Image saveImage(Image image)] is [NULL]");
        return null;
    }

    public List<Image> saveImages(List<Image> images, int entityId, ImageEntityType imageEntityType) {
        if(images != null) {
            try {
                for(Image image : images) {
                    if (image != null && !Objects.equals(image.getBase64String(), "")) {
                        final String SQL = "INSERT INTO momma_db.images (name_col, url, extension, size_in_bytes, date_added, entity_id, entity_type) VALUES(?, ?, ?, ?, ?, ?, ?)";

                        connection = ConnectionFactory.getConnection();
                        LOG.info("Executing query[" + SQL + "]");
                        preparedStatement = connection.prepareStatement(SQL);

                        preparedStatement.setString(1, image.getImageName());
                        preparedStatement.setString(2, image.getLocation());
                        preparedStatement.setString(3, image.getFileExtension());
                        preparedStatement.setDouble(4, image.getSize());
                        preparedStatement.setString(5, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
                        preparedStatement.setInt(6, entityId);
                        preparedStatement.setInt(7, imageEntityType.ordinal());

                        preparedStatement.execute();

                        LOG.info("Successfully saved image[" + image + "]");
                    }
                    else {
                        LOG.info("Null image found[" + image + "]");
                    }
                }
                return images;
            } catch (Exception e) {
                LOG.error("Failed to save image[" + images + "]", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        }
        LOG.error("Passed value in [public Image saveImage(Image image)] is [NULL]");
        return null;
    }

    public void deleteImage(int imageId) {
        try {
            final String SQL = "UPDATE momma_db.images SET is_active = '0', last_updated = ? WHERE id = ? ";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
            preparedStatement.setInt(2, imageId);
            preparedStatement.execute();
        } catch (Exception e) {
            LOG.error("Failed to delete image with id[" + imageId + "]", e);
        } finally {
            DatabaseUtil.close(connection, preparedStatement);
        }
    }

    public Image getImageById(int imageId) {
        try {
            final String SQL = "SELECT * FROM momma_db.images WHERE id = ?";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, imageId);

            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();
            Image image = null;
            if(resultSet.next()) {
                image = new Image();

                image.setId(resultSet.getInt("id"));
                image.setImageName(resultSet.getString("name_col"));
                image.setLocation(resultSet.getString("url"));
                image.setFileExtension(resultSet.getString("extension"));
                image.setSize(resultSet.getDouble("size_in_bytes"));
                image.setDateAdded(resultSet.getString("date_added"));
                image.setImageEntityType(ImageEntityType.getImageEntityType(resultSet.getInt("entity_type")));

                ImageUtil.setBase64StringToImages(image);
            }
            return image;
        } catch (Exception e) {
            LOG.info("Failed to get image with id[" + imageId + "]", e);
            return null;
        }
    }
}
