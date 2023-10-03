package com.healinghaven.bigmomma.service;

import com.healinghaven.bigmomma.entity.Product;
import com.healinghaven.bigmomma.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository repository;

    //GET methods
    public List<Product> getProducts() {
        try {
            LOG.info("Attempting to find all products from the DB");
            return repository.getProducts();
        } catch (Exception e) {
            LOG.error("Failed to get all products", e);
            throw e;
        }
    }

    public Product getProductById(int id) {
        try {
            LOG.info("Attempting to get product with id[" + id + "]");
            return repository.getProductById(id);
        } catch (Exception e) {
            LOG.error("Failed to get product with id[" + id + "]", e);
            return null;
        }
    }

    public List<Product> getProductByCategory(int category) {
        try {
            LOG.info("Attempting to get products with category[" + category + "]");
            return repository.getProductByCategory(category);
        } catch (Exception e) {
            LOG.error("Failed to get products by category [" + category + "]", e);
            return null;
        }
    }

    public List<Product> getProductByName(String name) {
        try {
            LOG.info("Attempting to find products with name[" + name + "]");
            return repository.getProductByName(name);
        } catch (Exception e) {
            LOG.error("Failed to get product by name [" + name +"]", e);
            throw e;
        }
    }

    //DELETE methods
    public String deleteProductById(int id) {
        try {
            repository.deleteProductById(id);
            LOG.info(String.format("SUCCESSFULLY deleted product[%s]", id));
            return String.format("%s - SUCCESSFULLY deleted product[%s]", HttpStatus.OK, id);
        } catch (Exception e) {
            LOG.error("Failed to delete product with id[" + id + "]", e);
            return String.format("%s FAILED due to[%s]",HttpStatus.INTERNAL_SERVER_ERROR , e);
        }
    }
    //PUT methods
    public Product saveProduct(Product product) {
        try {
            LOG.info("Attempting to save product[" + product.toString() + "] to DB");
            //return repository.save(product);
            return null;
        } catch (Exception e) {
            LOG.error("Failed to save product[" + product.toString() + "] to DB", e);
            throw e;
        }
    }

    public List<Product> saveProducts(List<Product> products) {
        try {
            LOG.info("Attempting to save products[" + products.toString() + "] to DB");
            //return repository.saveAll(products);
            return null;
        } catch (Exception e) {
            LOG.error("Failed to save product[" + products.toString() + "] to DB", e);
            throw e;
        }
    }
    //UPDATE methods
    public String updateProduct(Product product) throws SQLException {
        try {
            Product dbProduct = getProductById(product.getId());
            if(dbProduct != null) {
                LOG.info("Attempting to update product with id[" + product.getId() + "]");
                repository.updateProduct(product);
                return HttpStatus.OK + "-Product[" + product.getId() + "] successfully updated";
            } else {
                LOG.error(HttpStatus.INTERNAL_SERVER_ERROR + "-Failed to update product[" + product + "]");
                return HttpStatus.INTERNAL_SERVER_ERROR + "-Failed to update product[" + product.getId() + "]";
            }
        } catch (Exception e) {
            LOG.error(HttpStatus.INTERNAL_SERVER_ERROR + "-Failed to update product[" + product.getId() + "]", e);
            throw e;
        }
    }
}
