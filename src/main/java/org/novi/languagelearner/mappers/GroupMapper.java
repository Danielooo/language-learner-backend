package org.novi.languagelearner.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Component
public class GroupMapper {


    public Group mapToEntity(GroupRequestDTO groupRequestDTO, User user) {
        Group group = new Group();

        group.setGroupName(groupRequestDTO.getGroupName());

        group.setUser(user);

        List<Exercise> exercises = groupRequestDTO.getExercises();
        for (Exercise exercise : exercises) {
            exercise.setGroup(group);
        }
        group.setExercises(exercises);

        return group;
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
            ObjectMapper objectMapper = new ObjectMapper();
            GroupRequestDTO groupRequestDTO = objectMapper.readValue(jsonContent, GroupRequestDTO.class);
            groupRequestDTO.setUserName(userName);

            return groupRequestDTO;
        } catch (BadRequestException e) {
            throw new BadRequestException ("Error converting json file to GroupRequestDTO");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
