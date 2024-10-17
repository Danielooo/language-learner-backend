package org.novi.languagelearner.controllers;


import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> createGroup(@RequestBody @Valid GroupRequestDTO groupRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            groupRequestDTO.setUserName(authentication.getName());

            GroupResponseDTO groupResponseDTO = groupService.createGroup(groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/user/add-exercises")
    public ResponseEntity<?> addExercisesToGroup(@RequestBody @Valid GroupRequestDTO groupRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            groupRequestDTO.setUserName(userName);

            GroupResponseDTO groupResponseDTO = groupService.addExercisesToGroup(groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {

        try {

            GroupResponseDTO groupResponseDTO = groupService.getGroupById(id);

            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllGroupsOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            return ResponseEntity.ok().body(groupService.getAllGroups(userName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/user/upload-json-files")
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


    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllGroupsAsAdmin() {
        try {

            List<GroupResponseDTO> groupDTOs = groupService.getAllGroupsAsAdmin();

            return ResponseEntity.ok().body(groupDTOs);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("Something went wrong with retrieving all groups");
        }
    }


    // TODO: implement user authentication, only user that created group can delete group
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        try {
            groupService.deleteGroup(id);
            return ResponseEntity.ok().body(String.format("Group with id %d is deleted", id));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteGroupAsAdmin(@PathVariable Long id) {
        String message = groupService.deleteGroupAsAdmin(id);

        return ResponseEntity.ok().body(message);
    }
}
