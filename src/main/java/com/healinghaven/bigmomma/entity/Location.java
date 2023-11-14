package com.healinghaven.bigmomma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private String id;
    private String province;
    private String city;
    private String suburb;
    private String streetNumber;
    private String longitude;
    private String latitude;
    private String entityId;

    @Override
    public String toString() {
        return String.format("Location{province[%s], city[%s], suburb[%s], longitude[%s], latitude[%s]}", province, city, suburb, longitude, latitude);
    }
}
