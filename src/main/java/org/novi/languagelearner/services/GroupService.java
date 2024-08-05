package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.GroupRequestDTO;
import org.novi.languagelearner.dtos.GroupResponseDTO;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExcerciseMapper;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ExcerciseMapper excerciseMapper;
    private final UserService userService;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, ExcerciseMapper excerciseMapper, UserService userService) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.excerciseMapper = excerciseMapper;
        this.userService = userService;
    }


    public Long getUserIdByUserName(String userName) {
        Optional<User> user = userService.getUserByUserName(userName);
        if (user.isEmpty()) {
            throw new RecordNotFoundException(String.format("Username: %s not found in database", userName));
        }

        return user.get().getId();
    }
}
