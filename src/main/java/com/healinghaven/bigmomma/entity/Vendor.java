package com.healinghaven.bigmomma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor extends Entity{
    private String name;
    private String description;
    private String cellphoneNumber;
    private String emailAddress;
    private User owner;
    private Location location;
    private Image logo;
    private List<Product> products;

    @Override
    public String toString() {
        return String.format("Vendor{id [%s] name[%s], description[%s], owner[%s], location[%s]}", id, name, description, owner, location);
    }
}
