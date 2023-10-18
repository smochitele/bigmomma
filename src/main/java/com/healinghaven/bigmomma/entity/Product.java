package com.healinghaven.bigmomma.entity;

import com.healinghaven.bigmomma.enums.ProductCategory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private String instructions;
    private String color;
    private double price;
    private String bestBefore;
    private int quantity;
    private ProductCategory category;
    private List<Image> images;
    private double rating;
    private String dateAdded;
    private boolean isActive;
    private int owner;
    private String lastUpdated;

    @Override
    public String toString() {
        return String.format("Product {id[%s] name[%s] description[%s] color[%s] price[%s] bestBefore[%s] quantity[%s] rating[%s] isActive[%s] owner[%s]}", id, name, description, color, price, bestBefore, quantity, rating, isActive, owner);
    }
}
