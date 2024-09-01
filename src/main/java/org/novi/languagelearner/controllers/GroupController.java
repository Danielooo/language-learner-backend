package org.novi.languagelearner.controllers;


import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.services.GroupService;
import org.novi.languagelearner.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.novi.languagelearner.exceptions.AccessDeniedException;

import java.util.List;


// TODO: implement exception handling voor RecordNotFoundException
// TODO: Admin endpoints toevoegen
//
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    public GroupController(GroupService groupService, GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @PatchMapping("/add-exercises")
    public ResponseEntity<?> addExercisesToGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            groupRequestDTO.setUserName(userName);

            GroupResponseDTO groupResponseDTO = groupService.addExercisesToGroup(groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: implement user authentication
    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {

        try {

            GroupResponseDTO groupResponseDTO = groupService.getGroupById(id);

            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllGroupsOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            return ResponseEntity.ok().body(groupService.getAllGroups(userName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            groupRequestDTO.setUserName(authentication.getName());

            GroupResponseDTO groupResponseDTO = groupService.createGroup(groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: implement user authentication for adding userId to group
    @PostMapping("/upload-json-files")
    public ResponseEntity<?> createGroupsFromJsonFiles(@RequestBody MultipartFile[] files) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            List<GroupResponseDTO> listOfGroupResponseDTOs = groupService.createGroupsWithJsonFiles(files, userName);
            return ResponseEntity.ok().body(listOfGroupResponseDTOs);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroup(@PathVariable Long groupId, @RequestBody GroupRequestDTO groupRequestDTO) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            groupRequestDTO.setUserName(userName);
            groupRequestDTO.setId(groupId);

            GroupResponseDTO groupResponseDTO = groupService.updateGroup(groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("Error updating group");
        }
    }


    // TODO: implement user authentication, only user that created group can delete group
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        try {
            groupService.deleteGroup(id);
            return ResponseEntity.ok().body(String.format("Group with id %d is deleted", id));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Add admin deleteGroup
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteGroupAsAdmin(@PathVariable Long id) {
        if (!SecurityUtils.isCurrentUserAdmin()) {
            throw new AccessDeniedException("User with username: " + SecurityContextHolder.getContext().getAuthentication().getName() + " is no Admin");
        }

        String message = groupService.deleteGroupAsAdmin(id);

        return ResponseEntity.ok().body(message);
    }
}
