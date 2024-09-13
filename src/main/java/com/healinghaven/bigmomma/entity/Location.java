package com.healinghaven.bigmomma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location extends Entity{
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
