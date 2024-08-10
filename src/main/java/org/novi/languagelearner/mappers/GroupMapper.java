package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.GroupRequestDTO;
import org.novi.languagelearner.dtos.GroupResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.repositories.UserRepository;
import org.novi.languagelearner.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupMapper {

    private final UserRepository userRepository;
    private final UserService userService;


    public GroupMapper(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Group mapToEntity(GroupRequestDTO groupRequestDTO) {
        var group = new Group();
//        group.setUserId(userRepository.findByUserName(userName).get().getId());
        group.setGroupName(groupRequestDTO.getGroupName());

        List<Exercise> exercises = groupRequestDTO.getExercises();
        for (Exercise exercise : exercises) {
            exercise.setGroup(group); // Set the group reference in each exercise
        }
        group.setExercises(exercises);

        return group;
    }

    public Group mapInputIntoCurrentEntity(GroupRequestDTO groupRequestDTO, Group currentGroup) {
        Group updatedGroup = currentGroup;

        if (groupRequestDTO.getGroupName() != null) {
            updatedGroup.setGroupName(groupRequestDTO.getGroupName());
        }

        if (groupRequestDTO.getExercises() != null) {
            List<Exercise> exercises = groupRequestDTO.getExercises();
            for (Exercise exercise : exercises) {
                exercise.setGroup(updatedGroup); // Set the group reference in each exercise
            }
            updatedGroup.setExercises(exercises);
        }

        return updatedGroup;
    }

    public GroupResponseDTO mapToResponseDTO(Group group) {
        var groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setGroupName(group.getGroupName());
        groupResponseDTO.setExercises(group.getExercises());
        return groupResponseDTO;
    }
}
