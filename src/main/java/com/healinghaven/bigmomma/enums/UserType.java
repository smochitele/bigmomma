package com.healinghaven.bigmomma.enums;

public enum UserType {
    STANDARD("1", "This is standard user that can login and purchase"),
    VENDOR("2", "This is a user that owns a store"),
    ADMIN("3", "This is a super user");

    private String id;
    private  String description;

    UserType(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
