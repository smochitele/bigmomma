package com.healinghaven.bigmomma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    private String Id;
    private String name;
    private String description;
    private String cellphoneNumber;
    private String emailAddress;
    private User owner;
    private Location location;
    private int logoImageId;

    @Override
    public String toString() {
        return String.format("Vendor{name[%s], description[%s], owner[%s], location[%s]}", name, description, owner, location);
    }
}
