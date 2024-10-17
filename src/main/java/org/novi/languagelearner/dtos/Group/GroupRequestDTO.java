package org.novi.languagelearner.dtos.Group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.novi.languagelearner.entities.Exercise;

import java.util.List;

@Data
public class GroupRequestDTO {

    public GroupRequestDTO() {

    }
    public GroupRequestDTO(String groupName) {
        this.groupName = groupName;
    }

    private Long id;

    @NotBlank(message = "Group name can't be blank")
    @Size(min = 2,  max = 30, message = "Group name has to have a minimum of 2 and maximum of 30 characters")
    private String groupName;

    private String userName;
    private List<Exercise> exercises;
}
