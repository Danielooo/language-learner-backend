package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.Stats.StatsOfExerciseResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.StatsMapper;
import org.novi.languagelearner.services.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/stats")
public class  StatsController {


    private final StatsService statsService;
    private final StatsMapper statsMapper;


    public StatsController(StatsService statsService, StatsMapper statsMapper) {
        this.statsService = statsService;
        this.statsMapper = statsMapper;
    }


    @GetMapping("/user/exercise/{exerciseId}")
    public ResponseEntity<?> getStatsOfExercise(@PathVariable Long exerciseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            StatsOfExerciseResponseDTO statsResponseDTO = statsService.getStatsOfExercise(userName, exerciseId);

            return ResponseEntity.ok(statsResponseDTO);

        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("getStats failed: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}









