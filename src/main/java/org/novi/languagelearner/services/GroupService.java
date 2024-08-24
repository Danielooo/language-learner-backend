package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.Unsorted.GroupRequestDTO;
import org.novi.languagelearner.dtos.Unsorted.GroupResponseDTO;
import org.novi.languagelearner.dtos.Unsorted.UserResponseDTO;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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
        UserResponseDTO userResponseDTO = userService.getUserResponseDTOByUserName(userName);

        return userResponseDTO.getId();
    }

    public GroupResponseDTO createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = groupMapper.mapToEntity(groupRequestDTO);
        Group savedGroup = groupRepository.save(group);
        return groupMapper.mapToResponseDTO(savedGroup);
    }

    public List<GroupResponseDTO> createGroupsWithJsonFiles(MultipartFile[] jsonFiles) {

        try {
            List<GroupRequestDTO> groupRequestDTOList = new ArrayList<>();
            for (MultipartFile jsonFile : jsonFiles) {
                if (jsonFile.isEmpty()) {
                    throw new BadRequestException("Uploaded file is empty");
                }
                GroupRequestDTO groupRequestDTO = groupMapper.mapJsonFileToGroupRequestDTO(jsonFile);
                groupRequestDTOList.add(groupRequestDTO);
            }

            List<GroupResponseDTO> groupResponseDTOS = new ArrayList<>();
            for (GroupRequestDTO groupRequestDTO : groupRequestDTOList) {
                Group group = groupMapper.mapToEntity(groupRequestDTO);
                Group savedGroup = groupRepository.save(group);
                groupResponseDTOS.add(groupMapper.mapToResponseDTO(savedGroup));
            }

            return groupResponseDTOS;

        } catch (BadRequestException e) {
            throw new BadRequestException("Error persisting groups");
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

    public List<GroupResponseDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        if (groups.isEmpty()) {
            throw new RecordNotFoundException("No groups found");
        }
        List<GroupResponseDTO> dtos = new ArrayList<>();
        for (Group group : groups) {
            dtos.add(groupMapper.mapToResponseDTO(group));
        }
        return dtos;
    }
}
