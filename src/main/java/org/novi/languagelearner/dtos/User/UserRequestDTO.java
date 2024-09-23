package org.novi.languagelearner.dtos.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Username is required and cannot be blank")
    private String userName;

    @NotBlank(message = "Password is required and cannot be blank")
    private String password;

    @NotBlank(message = "First name is required and cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name is required and cannot be blank")
    private String lastName;

    @NotEmpty(message = "At least 1 role is required")
    private String[] roles;
}