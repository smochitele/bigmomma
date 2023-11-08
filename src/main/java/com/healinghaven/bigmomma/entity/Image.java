package com.healinghaven.bigmomma.entity;

import com.healinghaven.bigmomma.enums.ImageEntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private int id;
    private String imageName;
    private String base64String;
    private String location;
    private double size;
    private String dateAdded;
    private String fileExtension;
    private ImageEntityType imageEntityType;

    @Override
    public String toString() {
        return String.format("Image{id[%s], imageName[%s], location[%s], size[%s], fileExtension[%s]}", id, imageName, location, size, fileExtension);
    }
}
