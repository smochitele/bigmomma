package com.healinghaven.bigmomma.enums;

public enum UserStatus {
    INACTIVE,
    ACTIVE,
    BLOCKED;

    public static UserStatus getUserStatus(int ordinal) {
        for(UserStatus userStatus : UserStatus.values()) {
            if (userStatus.ordinal() == ordinal)
                return userStatus;
        }
        return INACTIVE;
    }
}
