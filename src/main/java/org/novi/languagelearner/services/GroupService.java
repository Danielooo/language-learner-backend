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

    public GroupResponseDTO createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = groupMapper.mapToEntity(groupRequestDTO);
        Optional<Group> savedGroup = groupRepository.save(group);
        if (savedGroup.isEmpty()) {
            throw new RecordNotFoundException("Group not saved. Record not found");
        } else {
            return groupMapper.mapToResponseDTO(savedGroup.get());
        }
    }

    public GroupResponseDTO updateGroup(Long id, GroupRequestDTO groupRequestDTO) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        } else {
            Group updatedGroup = groupMapper.mapToEntity(groupRequestDTO);
            updatedGroup.setId(id);
            Group savedGroup = groupRepository.save(updatedGroup);
            return groupMapper.mapToResponseDTO(savedGroup);
        }
    }
}
