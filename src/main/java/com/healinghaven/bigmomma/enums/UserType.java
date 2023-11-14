package com.healinghaven.bigmomma.enums;

public enum UserType {
    STANDARD("0", "This is standard user that can login and purchase"),
    VENDOR_OWNER("1", "This is a user that owns a store"),
    ADMIN("2", "This is a super user"),
    GUEST("2", "This is a guest user"),
    OTHER ("4", "Unknown user type");

    private String id;
    private  String description;

    UserType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static UserType getUserType(int ordinal) {
        for(UserType userType : UserType.values()) {
            if (userType.ordinal() == ordinal)
                return userType;
        }
        return OTHER;
    }
}
