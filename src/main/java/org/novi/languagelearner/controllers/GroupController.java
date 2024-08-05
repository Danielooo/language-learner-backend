package org.novi.languagelearner.controllers;


import org.novi.languagelearner.dtos.GroupRequestDTO;
import org.novi.languagelearner.dtos.GroupResponseDTO;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.GroupRepository;
import org.novi.languagelearner.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private Authentication authentication;


    @Autowired
    public GroupController(GroupService groupService, GroupMapper groupMapper, GroupRepository groupRepository) {
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
    }

    private void setAuthentication(SecurityContext context) {
        this.authentication =context.getAuthentication();
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        try {
//            setAuthentication(SecurityContextHolder.getContext());
//            String userName = authentication.getName();
            Group group = groupMapper.mapToEntity(groupRequestDTO);
            groupRepository.save(group);
            return ResponseEntity.ok(group);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
