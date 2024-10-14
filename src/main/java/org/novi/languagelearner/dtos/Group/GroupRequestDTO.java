package org.novi.languagelearner.dtos.Group;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.novi.languagelearner.entities.Exercise;
import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDTO {

    private Long id;

    @NotBlank(message = "Group name can't be blank")
    @Min(value = 1, message = "Group name must be at least 1 character long")
    private String groupName;

    private String userName;

    private List<Exercise> exercises;
}
