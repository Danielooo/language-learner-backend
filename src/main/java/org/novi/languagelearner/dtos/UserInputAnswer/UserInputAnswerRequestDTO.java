package org.novi.languagelearner.dtos.UserInputAnswer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserInputAnswerRequestDTO {

    @Positive(message = "Value must be 1 or higher")
    private Long exerciseId;

    @NotBlank(message = "User input cannot be blank")
    private String userInput;

    @NotBlank(message = "Username cannot be blank")
    private String userName;
}
