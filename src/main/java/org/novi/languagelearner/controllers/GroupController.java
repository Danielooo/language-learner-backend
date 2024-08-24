package org.novi.languagelearner.controllers;


import org.novi.languagelearner.dtos.Unsorted.GroupRequestDTO;
import org.novi.languagelearner.dtos.Unsorted.GroupResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


// TODO: implement exception handling voor RecordNotFoundException

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private Authentication authentication;


    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    private void setAuthentication(SecurityContext context) {
        this.authentication = context.getAuthentication();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {

        try {
            GroupResponseDTO groupResponseDTO = groupService.getGroupById(id);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllGroups() {
        try {
            return ResponseEntity.ok().body(groupService.getAllGroups());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        try {
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
            List<GroupResponseDTO> listOfGroupResponseDTOs = groupService.createGroupsWithJsonFiles(files);
            return ResponseEntity.ok().body(listOfGroupResponseDTOs);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Fix 500 error when putGroup 1 is called from Postman
    // TODO: implement user authentication for adding userId to group. Also only allow corresponding user to update group
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody GroupRequestDTO groupRequestDTO) {

        try {
            GroupResponseDTO groupResponseDTO = groupService.updateGroup(id, groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Works as PATCH
    @PutMapping("/patch/{id}")
    public ResponseEntity<?> updatePartOfGroup(@PathVariable Long id, @RequestBody GroupRequestDTO groupRequestDTO) {

        try {
            GroupResponseDTO groupResponseDTO = groupService.updatePartOfGroup(id, groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        try {
            groupService.deleteGroup(id);
            return ResponseEntity.ok().body(String.format("Group with id %d is deleted", id));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
