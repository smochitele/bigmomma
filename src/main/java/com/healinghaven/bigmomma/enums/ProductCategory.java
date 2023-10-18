package com.healinghaven.bigmomma.enums;

public enum ProductCategory {
    PRODUCT("0", "Tangible product"),
    SERVICE("1", "Intangible product"),
    OTHER("2", "Uncategorized");

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
