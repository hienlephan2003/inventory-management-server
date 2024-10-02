package org.inventory.management.server.model.user;

import lombok.Data;

@Data
public class SignInUserModel {
    private String username;
    private String password;
}
