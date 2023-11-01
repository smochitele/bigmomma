package com.healinghaven.bigmomma.enums;

public enum LocationSearchCriteria {
    CITY,
    PROVINCE,
    SUBURB,
    STREET_NUMBER;

    public static LocationSearchCriteria getVendorSearchCriteria(int ordinal) {
        for(LocationSearchCriteria criteria : LocationSearchCriteria.values()) {
            if (criteria.ordinal() == ordinal)
                return criteria;
        }
        return STREET_NUMBER;
    }

}
