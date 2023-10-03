package com.healinghaven.bigmomma.controller;

import com.healinghaven.bigmomma.entity.Product;
import com.healinghaven.bigmomma.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/api/addproduct")
    public Product addProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PostMapping("/api/addproducts")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return service.saveProducts(products);
    }

    @GetMapping("/api/getproducts")
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/api/getproductbyid/{id}")
    public Product getProductById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @GetMapping("/api/getproductbycategory/{category}")
    public List<Product> getProductsByCategory(@PathVariable int category) {
        return service.getProductByCategory(category);
    }

    @GetMapping("/api/getproductbyname/{name}")
    public List<Product> getProductByName(@PathVariable String name) {
        return service.getProductByName(name);
    }

    @PutMapping("/api/updateproduct")
    public String updateProduct(@RequestBody Product product) throws SQLException {
        return service.updateProduct(product);
    }

    @DeleteMapping("/api/deleteproductbyid/{id}")
    public String deleteProduct(@PathVariable int id) {
        return service.deleteProductById(id);
    }
}
