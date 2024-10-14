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
import org.novi.languagelearner.exceptions.UsernameNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.novi.languagelearner.repositories.GroupRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, UserService userService, UserRepository userRepository, ExerciseRepository exerciseRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }


    public GroupResponseDTO createGroup(GroupRequestDTO groupRequestDTO) {
        if (groupRepository.existsByGroupName(groupRequestDTO.getGroupName())) {
            throw new BadRequestException("Group with name " + groupRequestDTO.getGroupName() + " already exists, please choose another name");
        }

        Group group = groupMapper.mapToEntity(groupRequestDTO, userService.getUserByUserName(groupRequestDTO.getUserName()));
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
            Group group = groupMapper.mapToEntity(groupRequestDTO, userService.getUserByUserName(userName));
            Group savedGroup = groupRepository.save(group);
            groupResponseDTOS.add(groupMapper.mapToResponseDTO(savedGroup));
        }

        return groupResponseDTOS;
    }


    public GroupResponseDTO getGroupById(Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isEmpty()) {
            throw new RecordNotFoundException(String.format("Group with id: %d not found", id));
        }


        Group group = groupOptional.get();

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s is not found in database", userName)));

        if (group.getUser().getUserName().equals(user.getUserName())) {
            return groupMapper.mapToResponseDTO(group);
        } else {
            throw new AccessDeniedException("Group does not belong to user with username: " + userName);
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

    @Transactional
    public GroupResponseDTO addExercisesToGroup(GroupRequestDTO groupRequestDTO) {
        Group group = groupRepository.findById(groupRequestDTO.getId()).orElseThrow(() -> new RecordNotFoundException("Group not found with id: " + groupRequestDTO.getId()));
        if (!group.getUser().getUserName().equals(groupRequestDTO.getUserName())) {
            throw new AccessDeniedException("User with username: " + groupRequestDTO.getUserName() + " not authorized to add exercises to group with id: " + groupRequestDTO.getId());
        }


        for (Exercise exercise : groupRequestDTO.getExercises()) {
            Exercise exerciseToAdd = new Exercise();
            exerciseToAdd.setGroup(group);
            exerciseToAdd.setQuestion(exercise.getQuestion());
            exerciseToAdd.setAnswer(exercise.getAnswer());
            Exercise exerciseSaved = exerciseRepository.save(exerciseToAdd);
            group.getExercises().add(exerciseSaved);
        }

        Group savedGroup = groupRepository.save(group);
        return groupMapper.mapToResponseDTO(savedGroup);
    }


    public List<GroupResponseDTO> getAllGroupsAsAdmin() {
        List<Group> allGroups = groupRepository.findAll();
        if (allGroups.isEmpty()) {
            throw new RecordNotFoundException("No groups found in database");
        }

        List<GroupResponseDTO> responseDTOs = new ArrayList<>();

        for (Group group : allGroups) {
            responseDTOs.add(groupMapper.mapToResponseDTO(group));
        }
        return responseDTOs;
    }


    public String deleteGroupAsAdmin(Long id) {
        groupRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Group not found with following id: " + id));

        groupRepository.deleteById(id);

        return "Group deleted with following id: " + id;
    }
}


