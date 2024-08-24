package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.Stat.StatResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.StatMapper;
import org.novi.languagelearner.services.StatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// TODO: Feature build; getAnswers of UserController
// TODO: Feature build; create AnswersOfUserRequestDTO
// TODO: Feature build; create UserInputAnswerService
// TODO: Feature build; create UserInputAnswerRepository @Query params userName and exerciseId

@RestController
@RequestMapping("/stats")
public class StatController {


    private final StatService statService;
    private final StatMapper statMapper;


    public StatController(StatService statService, StatMapper statMapper) {
        this.statService = statService;
        this.statMapper = statMapper;
    }

    @GetMapping
    public ResponseEntity<?> getStats(@RequestParam Long exerciseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            StatResponseDTO statResponseDTO = statService.getUserInputAnswersByUser(userName, exerciseId);

            return ResponseEntity.ok(statResponseDTO);

        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("getStats failed: " + e.getMessage());
        }
    }






}
