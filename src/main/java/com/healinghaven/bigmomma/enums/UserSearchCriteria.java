package com.healinghaven.bigmomma.enums;

public enum UserSearchCriteria {
    ID,
    EMAIL_ADDRESS,
    CELLPHONE_NUMBER,
    USER_TYPE,
    FIRST_NAME,
    LAST_NAME;

    public static UserSearchCriteria searchCriteria(int ordinal) {
        for (UserSearchCriteria u : UserSearchCriteria.values()) {
            if (u.ordinal() == ordinal)
                return u;
        }
        return EMAIL_ADDRESS;
    }
}
