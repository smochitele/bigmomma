package com.healinghaven.bigmomma.entity;

import com.healinghaven.bigmomma.enums.ImageEntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image extends Entity{
    private String imageName;
    private String base64String;
    private String location;
    private double size;
    private String dateAdded;
    private String fileExtension;
    private int entityBelongingTo;
    private ImageEntityType imageEntityType;

    @Override
    public String toString() {
        return String.format("Image{id[%s], imageName[%s], location[%s], size[%s], fileExtension[%s]}", id, imageName, location, size, fileExtension);
    }
}
