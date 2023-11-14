package com.healinghaven.bigmomma.enums;


public enum ImageEntityType {
    PRODUCT_IMAGE,
    LOGO_IMAGE;

    public static ImageEntityType getImageEntityType(int ordinal) {
        for (ImageEntityType imageEntityType : ImageEntityType.values()) {
            if (imageEntityType.ordinal() == ordinal) {
                return imageEntityType;
            }
        }
        return PRODUCT_IMAGE;
    }
}
