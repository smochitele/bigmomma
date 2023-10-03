package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.healinghaven.bigmomma.enums.ProductCategory;
import com.healinghaven.bigmomma.utils.DatabaseUtil;
import com.healinghaven.bigmomma.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ProductRepository.class);
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
                    product.setBestBefore(DateUtil.getBasicDateFormat(resultSet.getString("best_before")));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("category"))));
                    product.setRating(resultSet.getFloat("rating"));
                    product.setDateAdded(DateUtil.getFullDateFormat(String.valueOf(resultSet.getDate("date_added"))));
                    product.setOwner(resultSet.getInt("product_owner"));
                    product.setActive(resultSet.getBoolean("is_active"));
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
                    product.setBestBefore(DateUtil.getBasicDateFormat(resultSet.getString("best_before")));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setCategory(ProductCategory.getProductCategory(String.valueOf(resultSet.getInt("category"))));
                    product.setRating(resultSet.getFloat("rating"));
                    product.setDateAdded(DateUtil.getFullDateFormat(String.valueOf(resultSet.getTimestamp("date_added"))));
                    product.setActive(resultSet.getBoolean("is_active"));

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
                    product.setDateAdded(DateUtil.getFullDateFormat(String.valueOf(resultSet.getDate("date_added"))));
                    product.setOwner(resultSet.getInt("product_owner"));
                    product.setActive(resultSet.getBoolean("is_active"));

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
                    product.setOwner(resultSet.getInt("product_owner"));
                    product.setActive(resultSet.getBoolean("is_active"));

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

    public void deleteProductById(int id) throws SQLException {
        try {
            final String SQL = "UPDATE momma_db.products SET is_active = '0' WHERE id = ? ";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            LOG.error("Failed to delete product with id[" + id + "]", e);
            throw e;
        } finally {
            DatabaseUtil.close(connection, preparedStatement);
        }
    }
}
