package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.GroupRequestDTO;
import org.novi.languagelearner.entities.Excercise;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.repositories.UserRepository;
import org.novi.languagelearner.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

        List<Excercise> excercises = groupRequestDTO.getExcercises();
        for (Excercise excercise : excercises) {
            excercise.setGroup(group); // Set the group reference in each exercise
        }
        group.setExcercises(excercises);

        return group;
    }
}
