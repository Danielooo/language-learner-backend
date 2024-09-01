package org.novi.languagelearner.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.repositories.UserRepository;
import org.novi.languagelearner.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Component
public class GroupMapper {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExerciseMapper exerciseMapper;


    public GroupMapper(UserRepository userRepository, UserService userService, ExerciseMapper exerciseMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.exerciseMapper = exerciseMapper;
    }

    public Group mapToEntity(GroupRequestDTO groupRequestDTO) {
        Group group = new Group();

        group.setGroupName(groupRequestDTO.getGroupName());

        group.setUser(userService.getUserByUserName(groupRequestDTO.getUserName()));

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


        return updatedGroup;
    }


    public GroupResponseDTO mapToResponseDTO(Group group) {
        var groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setId(group.getId());
        groupResponseDTO.setUserName(group.getUser().getUserName());
        groupResponseDTO.setGroupName(group.getGroupName());
        groupResponseDTO.setExercises(group.getExercises());
        return groupResponseDTO;
    }

    public GroupRequestDTO mapJsonFileToGroupRequestDTO(MultipartFile jsonFile, String userName)  {
        try {
            String jsonContent = new String(jsonFile.getBytes());

            GroupRequestDTO groupRequestDTO = objectMapper.readValue(jsonContent, GroupRequestDTO.class);
            groupRequestDTO.setUserName(userName);

            return groupRequestDTO;
        } catch (BadRequestException e) {
            throw new BadRequestException ("Error converting json file to GroupRequestDTO");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GroupRequestDTO mapControllerDataToRequestDTO (Long id) {
        GroupRequestDTO requestDTO = new GroupRequestDTO();
//        requestDTO.setUserName(userName);
        requestDTO.setId(id);

        return requestDTO;
    }
}
