package org.novi.languagelearner.dtos.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserNameChangeRequestDTO {

    @NotBlank(message = "Current username cannot be blank")
    private String currentUserName;

    @NotBlank(message = "New username cannot be blank")
    private String newUserName;
}
