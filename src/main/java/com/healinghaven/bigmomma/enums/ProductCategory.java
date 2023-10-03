package com.healinghaven.bigmomma.enums;

public enum ProductCategory {
    CLOTHING("0", "Clothing"),
    HERBS("1", "Herbs"),
    CANDLES("2", "Candles"),
    CONSULTATION("3", "Consultation"),
    OTHER("3", "Other");

    private final String id;
    private String description;
    ProductCategory(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static ProductCategory getProductCategory(String id) {
        for (ProductCategory productType : ProductCategory.values()) {
            if(productType.id.equals(id))
                return productType;
        }
        return ProductCategory.OTHER;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
