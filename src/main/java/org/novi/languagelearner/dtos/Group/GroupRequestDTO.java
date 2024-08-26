package org.novi.languagelearner.dtos.Group;

import org.novi.languagelearner.entities.Exercise;
import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDTO {

    private Long id;

    private String groupName;

    private String userName;

    private List<Exercise> exercises;


}
