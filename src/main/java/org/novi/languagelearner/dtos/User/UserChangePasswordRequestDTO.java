package org.novi.languagelearner.dtos.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserChangePasswordRequestDTO {

    @NotBlank(message = "New password cannot be blank")
    private String password;
}
