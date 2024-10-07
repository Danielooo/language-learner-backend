package org.novi.languagelearner.dtos.Group;

import jakarta.validation.constraints.Min;
import org.novi.languagelearner.entities.Exercise;
import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDTO {

//    @Min(value=0, message = "id can't be a negative integer")
    private Long id;

    private String groupName;

    private String userName;

    private List<Exercise> exercises;


}
