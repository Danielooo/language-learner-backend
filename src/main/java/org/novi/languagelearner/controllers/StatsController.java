package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.Stats.StatsResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.StatsMapper;
import org.novi.languagelearner.services.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stats")
public class StatsController {


    private final StatsService statsService;
    private final StatsMapper statsMapper;


    public StatsController(StatsService statsService, StatsMapper statsMapper) {
        this.statsService = statsService;
        this.statsMapper = statsMapper;
    }

    // TODO: implement timestamp for UserInputAnswer >> then: get for query param request. Use utility class for reading timestamp as time
    @GetMapping("/user/exercise")
    public ResponseEntity<?> getStatsOfExercise(@RequestParam Long exerciseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            StatsResponseDTO statsResponseDTO = statsService.getUserInputAnswersByUser(userName, exerciseId);

            return ResponseEntity.ok(statsResponseDTO);

        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("getStats failed: " + e.getMessage());
        }
    }

    // TODO: Stats contain the following: UserIntputAnswer (with timestamps), Group, ExerciseResponseDTO, Per exercise: timesright/timeswrong, lastInput: right/wrong, lastInputTimestamp
    // TODO: get all stats of user with user auth >> can use stat param for category, period of time
    // TODO: get all stats of user as admin






}
