package com.healinghaven.bigmomma.entity;

import com.healinghaven.bigmomma.enums.UserStatus;
import com.healinghaven.bigmomma.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String cellphoneNumber;
    private UserType userType;
    private AccessKey accessKey;
    private boolean isActive;
    private UserStatus userStatus;

    @Override
    public String toString() {
        return String.format("User{id[%s], firstName[%s], lastName[%s], emailAddress[%s], cellphoneNumber[%s], userType[%s]}", userId, firstName, lastName, emailAddress, cellphoneNumber, userType);
    }
}
