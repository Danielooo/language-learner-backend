package org.novi.languagelearner.dtos.Exercise;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExerciseRequestDTO {

    @Min(value=0, message = "id can't be a negative integer")
    public Long id;

    @NotBlank
    public String question;

    @NotBlank
    public String answer;


}
