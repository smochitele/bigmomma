package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public Product getProductById(int id) {
        try {
            final String SQL = "SELECT * FROM momma_db.products WHERE id = ? ";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);

            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                if(resultSet.getBoolean("is_active")) {
                    product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name_col"));
                    product.setDescription(resultSet.getString("description_col"));
                    product.setInstructions(resultSet.getString("instructions"));
                    product.setColor(resultSet.getString("color"));
                    product.setPrice(resultSet.getFloat("price"));
                    product.setBestBefore((resultSet.getString("best_before")));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("category"))));
                    product.setRating(resultSet.getFloat("rating"));
                    product.setDateAdded(String.valueOf(resultSet.getDate("date_added")));
                    product.setVendor(getVendorById(resultSet.getString("product_owner")).getName());
                    product.setActive(resultSet.getBoolean("is_active"));

                    product.setImages(imageService.getProductImages(product.getId()));
                    ImageUtil.setBase64StringToImages(product.getImages());

                    LOG.info("Returning product[" + product + "]");
                } else {
                    LOG.info("Product with product id[" + id + "] has been found but has an is_active status of[" + resultSet.getBoolean("is_active") + "]");
                }
                return product;
            } else {
                LOG.error("Product with id [" + id + "] was not found");
                return null;
            }
        } catch (Exception e) {
            LOG.error("Failed to read item id[" + id + "] from db", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }


    public List<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            final String SQL = "SELECT * FROM momma_db.products";
            connection = ConnectionFactory.getConnection();

            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getBoolean("is_active")) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name_col"));
                    product.setDescription(resultSet.getString("description_col"));
                    product.setInstructions(resultSet.getString("instructions"));
                    product.setColor(resultSet.getString("color"));
                    product.setPrice(resultSet.getFloat("price"));
                    product.setBestBefore((resultSet.getString("best_before")));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("category"))));
                    product.setRating(resultSet.getFloat("rating"));
                    product.setDateAdded((String.valueOf(resultSet.getTimestamp("date_added"))));
                    product.setActive(resultSet.getBoolean("is_active"));

                    product.setImages(imageService.getProductImages(product.getId()));
                    ImageUtil.setBase64StringToImages(product.getImages());

                    products.add(product);
                }
            }
            if (products.size() > 0) {
                LOG.info("Successfully returned [" + products.size() + "] products");
            } else {
                LOG.error("No products found on the DB");
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
        ArrayList<Product> products = new ArrayList<>();
        try {
            final String SQL = "SELECT * FROM momma_db.products WHERE category = ? ";
            connection = ConnectionFactory.getConnection();
            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, category);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getBoolean("is_active")) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name_col"));
                    product.setDescription(resultSet.getString("description_col"));
                    product.setInstructions(resultSet.getString("instructions"));
                    product.setColor(resultSet.getString("color"));
                    product.setPrice(resultSet.getFloat("price"));
                    product.setBestBefore(DateUtil.getBasicDateFormat(resultSet.getString("best_before")));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("category"))));
                    product.setRating(resultSet.getFloat("rating"));
                    product.setDateAdded(String.valueOf(resultSet.getDate("date_added")));
                    product.setVendor(vendorService.getVendorById(String.valueOf(resultSet.getInt("product_owner"))).getName());
                    product.setActive(resultSet.getBoolean("is_active"));

                    product.setImages(imageService.getProductImages(product.getId()));
                    ImageUtil.setBase64StringToImages(product.getImages());

                    products.add(product);
                }
            }
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
        ArrayList<Product> products = new ArrayList<>();
        try {
            final String SQL = "SELECT * FROM momma_db.products WHERE name_col like " + "'%" + name + "%'";
            connection = ConnectionFactory.getConnection();
            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getBoolean("is_active")) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name_col"));
                    product.setDescription(resultSet.getString("description_col"));
                    product.setInstructions(resultSet.getString("instructions"));
                    product.setColor(resultSet.getString("color"));
                    product.setPrice(resultSet.getFloat("price"));
                    product.setBestBefore(DateUtil.getBasicDateFormat(resultSet.getString("best_before")));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("category"))));
                    product.setRating(resultSet.getFloat("rating"));
                    product.setDateAdded(DateUtil.getFullDateFormat(String.valueOf(resultSet.getDate("date_added"))));
                    product.setVendor(vendorService.getVendorById(String.valueOf(resultSet.getInt("product_owner"))).getName());
                    product.setActive(resultSet.getBoolean("is_active"));

                    product.setImages(imageService.getProductImages(product.getId()));
                    ImageUtil.setBase64StringToImages(product.getImages());

                    products.add(product);
                } else {
                    LOG.info("Products with name[" + name + "] found but has an is_active status of [" + resultSet.next() + "]");
                }
            }
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
        ArrayList<Product> products = new ArrayList<>();
        try {
            final String SQL = "SELECT * FROM momma_db.products WHERE product_owner = ?";
            connection = ConnectionFactory.getConnection();

            LOG.info("Executing query[" + SQL + "]");
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, vendorId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getBoolean("is_active")) {
                    Product product = getProductFromResultSet(resultSet);

                    products.add(product);
                }
            }
            if (products.size() > 0) {
                LOG.info("Successfully returned [" + products.size() + "] products belonging to vendor with Id[" + vendorId + "]");
            } else {
                LOG.error("No products found on the DB");
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
            preparedStatement.setInt(10, Integer.valueOf(getVendorByName(product.getName()).getId()));
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
        try {
            if(product != null) {
                final String SQL = "INSERT INTO momma_db.products (name_col, description_col, instructions, color, price, best_before, quantity, category, product_owner) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                connection = ConnectionFactory.getConnection();
                LOG.info("Executing query[" + SQL + "]");
                preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                setProductPreparedStatement(preparedStatement,product);

                preparedStatement.execute();

                try {
                    if(product.getImages() != null) {
                        preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()", Statement.RETURN_GENERATED_KEYS);
                        ResultSet rs = preparedStatement.executeQuery();
                        if(rs.next()) {
                            int productId = rs.getInt("LAST_INSERT_ID()");
                            LOG.info("Saving product images for product[" + productId + "]");
                            imageService.saveImages(product.getImages(), productId, ImageEntityType.PRODUCT_IMAGE);
                        }
                    }
                } catch (Exception e) {
                    LOG.info("Error saving images related to product[" + product + "]", e);
                }

                LOG.info("Successfully added product[" + product + "] to DB");
                return product;
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
        try {
            if(products != null) {
                products.forEach(p -> {
                    if(p != null) {
                        final String SQL = "INSERT INTO momma_db.products (name_col, description_col, instructions, color, price, best_before, quantity, category, product_owner) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        connection = ConnectionFactory.getConnection();
                        LOG.info("Executing query[" + SQL + "]");
                        try {
                            preparedStatement = connection.prepareStatement(SQL);
                            setProductPreparedStatement(preparedStatement, p);

                            preparedStatement.execute();

                            try {
                                preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()", Statement.RETURN_GENERATED_KEYS);
                                ResultSet rs = preparedStatement.executeQuery();
                                if(rs.next()) {
                                    int productId = rs.getInt("LAST_INSERT_ID()");
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
            LOG.error("Failed to save products[" + products + "] to DB");
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                Product product = new Product();

                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name_col"));
                product.setDescription(resultSet.getString("description_col"));
                product.setInstructions(resultSet.getString("instructions"));
                product.setColor(resultSet.getString("color"));
                product.setPrice(resultSet.getFloat("price"));
                product.setBestBefore((resultSet.getString("best_before")));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("category"))));
                product.setRating(resultSet.getFloat("rating"));
                product.setDateAdded((String.valueOf(resultSet.getTimestamp("date_added"))));
                product.setActive(resultSet.getBoolean("is_active"));
                product.setVendor(getVendorById(resultSet.getString("product_owner")).getName());
                product.setDateAdded(resultSet.getString("date_added"));

                product.setImages(new ImageRepository().getProductImages(product.getId()));
                ImageUtil.setBase64StringToImages(product.getImages());

                return product;
            } catch (Exception e) {
                LOG.error("Failed to get product from result set", e);
                return null;
            }
        } else {
            LOG.warn("Result set null, returning a null product");
            return null;
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
            preparedStatement.setInt(9, Integer.parseInt(getVendorByName(p.getVendor()).getId()));
        } catch (Exception e) {
            LOG.error("Failed to set set up prepared statement", e);
        }
    }


    private Vendor getVendorById(String vendorId) {
        return new VendorRepository().getVendorById(vendorId, false);
    }


    private Vendor getVendorByName(String vendorName) {
        return new VendorRepository().getVendorByName(vendorName, false);
    }
}
