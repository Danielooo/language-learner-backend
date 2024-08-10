package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.GroupRequestDTO;
import org.novi.languagelearner.dtos.GroupResponseDTO;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ExerciseMapper exerciseMapper;
    private final UserService userService;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, ExerciseMapper exerciseMapper, UserService userService) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.exerciseMapper = exerciseMapper;
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
        Group savedGroup = groupRepository.save(group);
        return groupMapper.mapToResponseDTO(savedGroup);
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

    public GroupResponseDTO updatePartOfGroup(Long id, GroupRequestDTO groupRequestDTO) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        } else {
//            Group updatedGroup = group.get();
            Group updatedGroup = groupMapper.mapInputIntoCurrentEntity(groupRequestDTO, group.get());
            updatedGroup.setId(id);
            Group savedGroup = groupRepository.save(updatedGroup);
            return groupMapper.mapToResponseDTO(savedGroup);
        }
    }

    public GroupResponseDTO getGroupById(Long id) {

        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        } else {
            return groupMapper.mapToResponseDTO(group.get());
        }
    }

    public GroupResponseDTO deleteGroup(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        } else {
            groupRepository.delete(group.get());
            return groupMapper.mapToResponseDTO(group.get());
        }
    }
}
