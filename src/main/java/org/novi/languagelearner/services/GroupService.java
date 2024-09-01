package org.novi.languagelearner.services;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.dtos.User.UserResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.GroupRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.novi.languagelearner.exceptions.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ExerciseMapper exerciseMapper;
    private final UserService userService;
    private final ExerciseService exerciseService;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, ExerciseMapper exerciseMapper, UserService userService, ExerciseService exerciseService, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.exerciseMapper = exerciseMapper;
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.userRepository = userRepository;
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

    public List<GroupResponseDTO> createGroupsWithJsonFiles(MultipartFile[] jsonFiles, String userName) {

            List<GroupRequestDTO> groupRequestDTOList = new ArrayList<>();
            for (MultipartFile jsonFile : jsonFiles) {
                if (jsonFile.isEmpty()) {
                    throw new BadRequestException("Uploaded file is empty");
                }
                GroupRequestDTO groupRequestDTO = groupMapper.mapJsonFileToGroupRequestDTO(jsonFile, userName);
                groupRequestDTOList.add(groupRequestDTO);
            }

            List<GroupResponseDTO> groupResponseDTOS = new ArrayList<>();
            for (GroupRequestDTO groupRequestDTO : groupRequestDTOList) {
                Group group = groupMapper.mapToEntity(groupRequestDTO);
                Group savedGroup = groupRepository.save(group);
                groupResponseDTOS.add(groupMapper.mapToResponseDTO(savedGroup));
            }

            return groupResponseDTOS;
    }



    @Transactional
    public GroupResponseDTO updateGroup( GroupRequestDTO groupRequestDTO) {
        Optional<Group> groupOptional = groupRepository.findById(groupRequestDTO.getId());
        if (groupOptional.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", groupRequestDTO.getId()));
        } else {
            Group groupUpdated = groupMapper.mapInputIntoCurrentEntity(groupRequestDTO, groupOptional.get());

            Group savedGroup = groupRepository.save(groupUpdated);
            return groupMapper.mapToResponseDTO(savedGroup);
        }
    }

    // TODO: test if this is working and has a use case
    public GroupResponseDTO updatePartOfGroup(Long id, GroupRequestDTO groupRequestDTO) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        } else {
//            Group updatedGroup = group.get();
            Group updatedGroup = groupMapper.mapInputIntoCurrentEntity(groupRequestDTO, group.get());
            Group savedGroup = groupRepository.save(updatedGroup);
            return groupMapper.mapToResponseDTO(savedGroup);
        }
    }

    // TODO: implement user authentication
    public GroupResponseDTO getGroupById(Long id) {

        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        } else {
            return groupMapper.mapToResponseDTO(group.get());
        }
    }


    // TODO: implement user authentication, only user that created group can delete group
    public GroupResponseDTO deleteGroup(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        } else {
            groupRepository.delete(group.get());
            return groupMapper.mapToResponseDTO(group.get());
        }
    }

    public String deleteGroupAsAdmin(Long id) {
        groupRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Group not found with following id: " + id));

        groupRepository.deleteById(id);

        return "Group deleted with following id: " + id;
    }


    // TODO: implement user authentication
    public List<GroupResponseDTO> getAllGroups(String userName) {

        List<Group> groups = groupRepository.findByUser_UserName(userName);

        if (groups.isEmpty()) {

            throw new RecordNotFoundException("No groups found");
        } else {

            return groups
                .stream()
                .map(groupMapper::mapToResponseDTO)
                .collect(Collectors.toList());
        }
    }

    public GroupResponseDTO addExercisesToGroup(GroupRequestDTO groupRequestDTO) throws AccessDeniedException {


        Group group = groupRepository.findById(groupRequestDTO.getId()).orElseThrow(() -> new RecordNotFoundException("Group not found"));
        if ( group.getUser().getUserName().equals(groupRequestDTO.getUserName()) ) {
            throw new AccessDeniedException("User not authorized to add exercises to group");
        }

        List<Exercise> exercises = groupRequestDTO.getExercises();
        for (Exercise exercise : exercises) {
            exercise.setGroup(group);
            group.getExercises().add(exercise);
        }

        Group savedGroup = groupRepository.save(group);
        return groupMapper.mapToResponseDTO(savedGroup);
    }
}
