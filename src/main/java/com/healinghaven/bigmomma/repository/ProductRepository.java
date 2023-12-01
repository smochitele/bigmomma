package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.Image;
import com.healinghaven.bigmomma.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.healinghaven.bigmomma.entity.Vendor;
import com.healinghaven.bigmomma.enums.ImageEntityType;
import com.healinghaven.bigmomma.enums.ProductCategory;
import com.healinghaven.bigmomma.service.ImageService;
import com.healinghaven.bigmomma.service.VendorService;
import com.healinghaven.bigmomma.utils.DatabaseUtil;
import com.healinghaven.bigmomma.utils.DateUtil;
import com.healinghaven.bigmomma.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    private ImageService imageService;
    @Autowired
    private VendorService vendorService;

    public Product getProductById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String SQL = """
                    SELECT *
                    FROM momma_db.products AS p
                    INNER JOIN momma_db.images AS i ON p.id = i.entity_id
                    INNER JOIN momma_db.vendors AS v ON v.vendor_id = p.product_owner
                    WHERE p.id = ?
                    """;
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();
            Product product = getProductFromResultSet(resultSet);
            LOG.info("Returning product[" + product + "]");
            return product;
        } catch (Exception e) {
            LOG.error("Failed to read item id[" + id + "] from db", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }


    public List<Product> getProducts() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Product> products = null;
        try {
            final String SQL = """
                    SELECT *
                    FROM momma_db.products AS p
                    INNER JOIN momma_db.images AS i ON p.id = i.entity_id
                    INNER JOIN momma_db.vendors AS v ON v.vendor_id = p.product_owner
                    """;

            connection = ConnectionFactory.getConnection();

            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);
            resultSet = preparedStatement.executeQuery();
            products = getProductsFromResultSet(resultSet);
            assert products != null;
            if (products.size() > 0) {
                LOG.info("Successfully returned [" + products.size() + "] products");
            } else {
                LOG.warn("No products found on the DB");
            }
            return products;
        } catch (Exception e) {
            LOG.error("Failed to retrieve products", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public List<Product> getProductByCategory(int category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Product> products;
        try {
            final String SQL = """
                    SELECT *
                    FROM momma_db.products AS p
                    INNER JOIN momma_db.images AS i ON p.id = i.entity_id
                    INNER JOIN momma_db.vendors AS v ON v.vendor_id = p.product_owner
                    WHERE p.category = ?
                    """;
            connection = ConnectionFactory.getConnection();
            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, category);
            resultSet = preparedStatement.executeQuery();
            products = getProductsFromResultSet(resultSet);
            assert products != null;
            if(products.size() > 0) {
                LOG.info("Successfully returned [" + products.size() + "] products with category [" + category + "]");
            } else {
                LOG.error("No products found for category[" + category + "]");
            }
            return products;
        } catch (Exception e) {
            LOG.error("Failed get products with category[" + category + "] from db", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public ArrayList<Product> getProductByName(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
             String SQL = """
                    SELECT *
                    FROM momma_db.products AS p
                    INNER JOIN momma_db.images AS i ON p.id = i.entity_id
                    INNER JOIN momma_db.vendors AS v ON v.vendor_id = p.product_owner
                    WHERE p.name_col like\s
                    """;
            SQL += "'%" + name + "%'";

            connection = ConnectionFactory.getConnection();
            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);

            resultSet = preparedStatement.executeQuery();
            products = getProductsFromResultSet(resultSet);
            assert products != null;
            if(products.size() > 0) {
                LOG.info("Successfully returned [" + products.size() + "] products with name [" + name + "]");
            } else {
                LOG.error("No products found for name[" + name + "]");
            }
            return products;
        } catch (Exception e) {
            LOG.error("Failed get products with name[" + name + "] from db", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public List<Product> getVendorProducts(int vendorId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Product> products;
        try {
            final String SQL = """
                    SELECT *
                    FROM momma_db.products AS p
                    INNER JOIN momma_db.images AS i ON p.id = i.entity_id
                    INNER JOIN momma_db.vendors AS v ON v.vendor_id = p.product_owner
                    WHERE p.product_owner = ?
                    """;
            connection = ConnectionFactory.getConnection();

            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, vendorId);
            resultSet = preparedStatement.executeQuery();
            products = getProductsFromResultSet(resultSet);
            assert products != null;
            if (products.size() > 0) {
                LOG.info("Successfully returned [" + products.size() + "] products belonging to vendor with Id[" + vendorId + "]");
            } else {
                LOG.warn("No products found on the DB for vendor with ID[" + vendorId + "]");
            }
            return products;
        } catch (Exception e) {
            LOG.error("Failed to retrieve products", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public void deleteProductById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String SQL = "UPDATE momma_db.products SET is_active = '0', last_updated = ? WHERE id = ? ";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
            preparedStatement.setInt(2, id);
            LOG.info("Deleting product by query[" + SQL + "]");
            preparedStatement.execute();
        } catch (Exception e) {
            LOG.error("Failed to delete product with id[" + id + "]", e);
            throw e;
        } finally {
            DatabaseUtil.close(connection, preparedStatement);
        }
    }

    public void updateProduct(Product product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String SQL = "UPDATE momma_db.products " +
                                "SET name_col = ?," +
                                    "description_col = ?," +
                                    "instructions = ?," +
                                    "color = ?," +
                                    "price = ?," +
                                    "best_before = ?," +
                                    "quantity = ?," +
                                    "category = ?," +
                                    "rating = ?," +
                                    "product_owner = ?," +
                                    "is_active = ?," +
                                    "date_added = ?," +
                                    "last_updated = ? " +
                                "WHERE id = ?";
            connection = ConnectionFactory.getConnection();
            LOG.info("Executing query[" + SQL + "]");

            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getInstructions());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setFloat(5, (float) product.getPrice());
            preparedStatement.setString(6, product.getBestBefore());
            preparedStatement.setInt(7, product.getQuantity());
            preparedStatement.setInt(8, product.getCategory().ordinal());
            preparedStatement.setFloat(9, (float) product.getRating());
            preparedStatement.setInt(10, product.getVendor().getId());
            preparedStatement.setBoolean(11, product.isActive());
            preparedStatement.setString(12, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
            preparedStatement.setString(13, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
            preparedStatement.setInt(14, product.getId());

            preparedStatement.execute();
        } catch (Exception e) {
            LOG.error("Failed to update product[" + product + "]", e);
            throw e;
        } finally {
            DatabaseUtil.close(connection, preparedStatement);
        }
    }

    public Product saveProduct(Product product) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            if(product != null) {
                final String SQL = "INSERT INTO momma_db.products (name_col, description_col, instructions, color, price, best_before, quantity, category, product_owner) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                connection = ConnectionFactory.getConnection();
                LOG.info("Executing query[" + SQL + "]");
                preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                setProductPreparedStatement(preparedStatement,product);

                preparedStatement.execute();
                int productId = 0;
                try {
                    if(product.getImages() != null) {
                        ResultSet rs = preparedStatement.getGeneratedKeys();
                        if(rs.next()) {
                            productId = rs.getInt(1);
                            LOG.info("Saving product images for product[" + productId + "]");
                            imageService.saveImages(product.getImages(), productId, ImageEntityType.PRODUCT_IMAGE);
                        }
                    }
                } catch (Exception e) {
                    LOG.info("Error saving images related to product[" + product + "]", e);
                }

                LOG.info("Successfully added product[" + product + "] to DB");
                return getProductById(productId);
            } else {
                LOG.error("Cannot save null product to DB");
                return null;
            }
        } catch (Exception e) {
            LOG.error("Failed to save product[" + product + "] top DB", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement);
        }
    }

    public List<Product> saveAllProducts(List<Product> products) {
        AtomicReference<Connection> connection = null;
        AtomicReference<PreparedStatement> preparedStatement = null;
        try {
            if(products != null) {
                products.forEach(p -> {
                    if(p != null) {
                        final String SQL = "INSERT INTO momma_db.products (name_col, description_col, instructions, color, price, best_before, quantity, category, product_owner) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        connection.set(ConnectionFactory.getConnection());
                        LOG.info("Executing query[" + SQL + "]");
                        try {
                            preparedStatement.set(connection.get().prepareStatement(SQL));
                            setProductPreparedStatement(preparedStatement.get(), p);

                            preparedStatement.get().execute();

                            try {
                                ResultSet rs = preparedStatement.get().getGeneratedKeys();
                                if(rs.next()) {
                                    int productId = rs.getInt(1);
                                    LOG.info("Saving product images for product[" + productId + "]");
                                    imageService.saveImages(p.getImages(), productId, ImageEntityType.PRODUCT_IMAGE);
                                }
                            } catch (Exception e) {
                                LOG.info("Error saving images related tp product[" + p + "]");
                            }

                            LOG.info("Successfully added product[" + p + "] to DB");
                        } catch (SQLException e) {
                            LOG.error("Failed to write to DB", e);
                            throw new RuntimeException(e);
                        }
                    } else {
                        LOG.error("Found null product in the list, not saving null product to DB");
                    }
                });
                return products;
            } else {
                LOG.error("Cannot save null products to DB");
                return null;
            }
        } catch (Exception e) {
            LOG.error("Failed to save products[" + products + "] to DB", e);
            return null;
        } finally {
            DatabaseUtil.close(connection.get(), preparedStatement.get());
        }
    }

    private void setProductPreparedStatement(PreparedStatement preparedStatement, Product p) {
        try {
            preparedStatement.setString(1, p.getName());
            preparedStatement.setString(2, p.getDescription());
            preparedStatement.setString(3, p.getInstructions());
            preparedStatement.setString(4, p.getColor());
            preparedStatement.setFloat(5, (float) p.getPrice());
            preparedStatement.setString(6, p.getBestBefore());
            preparedStatement.setInt(7, p.getQuantity());
            preparedStatement.setInt(8, p.getCategory().ordinal());
            preparedStatement.setInt(9, p.getVendor().getId());
            preparedStatement.setString(10, p.getVendor().getName());
        } catch (Exception e) {
            LOG.error("Failed to set set up prepared statement", e);
        }
    }

    private ArrayList<Product> getProductsFromResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                ArrayList<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    if (resultSet.getBoolean("p.is_active")) {
                        Product product = new Product();
                        Image image = new Image();
                        Vendor vendor = new Vendor();

                        product.setId(resultSet.getInt("p.id"));
                        product.setName(resultSet.getString("p.name_col"));
                        product.setDescription(resultSet.getString("p.description_col"));
                        product.setInstructions(resultSet.getString("p.instructions"));
                        product.setColor(resultSet.getString("p.color"));
                        product.setPrice(resultSet.getFloat("p.price"));
                        product.setBestBefore((resultSet.getString("p.best_before")));
                        product.setQuantity(resultSet.getInt("p.quantity"));
                        product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("p.category"))));
                        product.setRating(resultSet.getFloat("p.rating"));
                        product.setDateAdded((String.valueOf(resultSet.getTimestamp("p.date_added"))));
                        product.setActive(resultSet.getBoolean("p.is_active"));

                        image.setId(resultSet.getInt("i.id"));
                        image.setImageName(resultSet.getString("i.name_col"));
                        image.setLocation(resultSet.getString("i.url"));
                        image.setFileExtension(resultSet.getString("i.extension"));
                        image.setSize(resultSet.getDouble("i.size_in_bytes"));
                        image.setDateAdded(resultSet.getString("i.date_added"));
                        image.setImageEntityType(ImageEntityType.PRODUCT_IMAGE);


                        vendor.setId(resultSet.getInt("v.vendor_id"));
                        vendor.setName(resultSet.getString("v.vendor_name"));
                        vendor.setEmailAddress(resultSet.getString("v.email_address"));
                        vendor.setCellphoneNumber(resultSet.getString("v.cellphone_number"));
                        vendor.setDateAdded(resultSet.getString("v.date_added"));
                        vendor.setDescription(resultSet.getString("v.vendor_description"));

                        if(!products.contains(product)) {
                            product.setVendor(vendor);
                            product.setVendor(vendor);
                            product.addImage(image);
                            ImageUtil.setBase64StringToImages(image);
                            products.add(product);
                        } else {
                            products.get(products.indexOf(product)).addImage(image);
                        }
                    }
                }
                return products;
            } catch (Exception e) {
                LOG.error("Failed to get products from result set[" + resultSet + "]", e);
                return null;
            }
        } else {
            LOG.warn("Null resultSet passed in method[private List<Product> getProductsFromResultSet(ResultSet resultSet)]");
            return null;
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                Product product = new Product();
                while (resultSet.next()) {
                    if (resultSet.getBoolean("p.is_active")) {
                        Image image = new Image();
                        Vendor vendor = new Vendor();

                        product.setId(resultSet.getInt("p.id"));
                        product.setName(resultSet.getString("p.name_col"));
                        product.setDescription(resultSet.getString("p.description_col"));
                        product.setInstructions(resultSet.getString("p.instructions"));
                        product.setColor(resultSet.getString("p.color"));
                        product.setPrice(resultSet.getFloat("p.price"));
                        product.setBestBefore((resultSet.getString("p.best_before")));
                        product.setQuantity(resultSet.getInt("p.quantity"));
                        product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("p.category"))));
                        product.setRating(resultSet.getFloat("p.rating"));
                        product.setDateAdded((String.valueOf(resultSet.getTimestamp("p.date_added"))));
                        product.setActive(resultSet.getBoolean("p.is_active"));

                        image.setId(resultSet.getInt("i.id"));
                        image.setImageName(resultSet.getString("i.name_col"));
                        image.setLocation(resultSet.getString("i.url"));
                        image.setFileExtension(resultSet.getString("i.extension"));
                        image.setSize(resultSet.getDouble("i.size_in_bytes"));
                        image.setDateAdded(resultSet.getString("i.date_added"));
                        image.setImageEntityType(ImageEntityType.PRODUCT_IMAGE);


                        vendor.setId(resultSet.getInt("v.vendor_id"));
                        vendor.setName(resultSet.getString("v.vendor_name"));
                        vendor.setEmailAddress(resultSet.getString("v.email_address"));
                        vendor.setCellphoneNumber(resultSet.getString("v.cellphone_number"));
                        vendor.setDateAdded(resultSet.getString("v.date_added"));
                        vendor.setDescription(resultSet.getString("v.vendor_description"));

                        product.setVendor(vendor);
                        product.setVendor(vendor);
                        product.addImage(image);
                        ImageUtil.setBase64StringToImages(image);
                    }
                }
                return product;
            } catch (Exception e) {
                LOG.error("Failed to get products from result set[" + resultSet + "]", e);
                return null;
            }
        } else {
            LOG.warn("Null resultSet passed in method[private List<Product> getProductsFromResultSet(ResultSet resultSet)]");
            return null;
        }
    }
}
