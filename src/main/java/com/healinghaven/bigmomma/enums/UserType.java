package com.healinghaven.bigmomma.enums;

public enum UserType {
    STANDARD("1", "This is standard user that can login and purchase"),
    VENDOR("2", "This is a user that owns a store"),
    ADMIN("3", "This is a super user"),
    GUEST("4", "This is a guest user"),
    OTHER ("5", "Unknown user type");

    private String id;
    private  String description;

    UserType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static UserType getUserType(String id) {
        for(UserType userType : UserType.values()) {
            if (userType.id.equals(id))
                return userType;
        }
        return OTHER;
    }
}
