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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// MAPPING SEQUENCE
// service aanroepen
// maak daar van requestdto een entity
// vanuit servise persisten
// vanuit repository krijg je Optional terug in de Service
// In de service optional logica (isPresent)
// Vanuit service mappen naar ResponseDTO en en teruggeven aan controller
// vanuit controller ResponseDTO teruggeven aan client (Of BadRequestException)

// TODO: add GET for group by id
// TODO: add GET for all groups
// TODO: add PUT for group by id
// TODO: add DELETE for group by id
// TODO: implement exception handling

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private Authentication authentication;


    @Autowired
    public GroupController(GroupService groupService, GroupMapper groupMapper, GroupRepository groupRepository) {
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }

    private void setAuthentication(SecurityContext context) {
        this.authentication = context.getAuthentication();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            return ResponseEntity.ok(groupMapper.mapToResponseDTO(group.get()));
        } else {
            return ResponseEntity.notFound().build();
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody GroupRequestDTO groupRequestDTO) {

        // service aanroepen
        // maak daar van requestdto een entity
        // vanuit servise persisten
        // vanuit repository krijg je Optional terug in de Service
        // In de service optional logica (isPresent)
        // Vanuit service mappen naar ResponseDTO en en teruggeven aan controller
        // vanuit controller ResponseDTO teruggeven aan client (Of BadRequestException)

        try {
            GroupResponseDTO groupResponseDTO = groupService.updateGroup(id, groupRequestDTO);
            return ResponseEntity.ok().body(groupResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
