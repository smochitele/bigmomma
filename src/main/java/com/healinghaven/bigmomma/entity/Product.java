package com.healinghaven.bigmomma.entity;

import com.healinghaven.bigmomma.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Entity {
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
    private boolean isActive;
    private Vendor vendor;

    public void addImage(Image image) {
        if(images != null) {
            images.add(image);
        } else {
            images = new ArrayList<>();
            images.add(image);
        }
    }
    @Override
    public String toString() {
        return String.format("Product {id[%s] name[%s] description[%s] color[%s] price[%s] bestBefore[%s] quantity[%s] rating[%s] isActive[%s] owner[%s]}", id, name, description, color, price, bestBefore, quantity, rating, isActive, vendor);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
