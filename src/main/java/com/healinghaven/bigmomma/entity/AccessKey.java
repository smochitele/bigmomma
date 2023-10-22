package com.healinghaven.bigmomma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessKey {
    private String email;
    private String password;
    private String lastLogonDevicePlatform;
    private String lastLogonDateTime;
}
