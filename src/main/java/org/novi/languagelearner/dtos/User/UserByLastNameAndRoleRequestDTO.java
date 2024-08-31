package org.novi.languagelearner.dtos.User;

import lombok.Data;

@Data
public class UserByLastNameAndRoleRequestDTO {

    private String adminUserName;
    private String lastName;
    private String role;
}
